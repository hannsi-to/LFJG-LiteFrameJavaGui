package me.hannsi.lfjg.audio;

import me.hannsi.lfjg.core.utils.reflection.location.Location;
import org.bytedeco.ffmpeg.avcodec.AVCodec;
import org.bytedeco.ffmpeg.avcodec.AVCodecContext;
import org.bytedeco.ffmpeg.avcodec.AVCodecParameters;
import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.ffmpeg.avutil.AVDictionary;
import org.bytedeco.ffmpeg.avutil.AVFrame;
import org.bytedeco.ffmpeg.swresample.SwrContext;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.PointerPointer;

import java.nio.ByteBuffer;

import static org.bytedeco.ffmpeg.global.avcodec.*;
import static org.bytedeco.ffmpeg.global.avformat.*;
import static org.bytedeco.ffmpeg.global.avutil.*;
import static org.bytedeco.ffmpeg.global.swresample.*;

public class FFmpegAudioSupplier implements AudioStreamingBuffer.AudioSupplier {
    private final AVFormatContext formatContext;
    private final AVCodecContext codecContext;
    private final SwrContext swr;
    private final AVFrame frame;
    private final AVPacket packet;

    private int audioStreamIndex;
    private boolean finished = false;
    private BytePointer reuseBuffer;

    public FFmpegAudioSupplier(Location location) {
        avformat_network_init();

        formatContext = avformat_alloc_context();
        switch (location.locationType()) {
            case RESOURCE, FILE ->
                    avformat_open_input(formatContext, location.path(), null, null);
            default ->
                    throw new IllegalStateException("Unexpected value: " + location);
        }
        avformat_find_stream_info(formatContext, (PointerPointer<?>) null);

        for (int i = 0; i < formatContext.nb_streams(); i++) {
            if (formatContext.streams(i).codecpar().codec_type() == AVMEDIA_TYPE_AUDIO) {
                audioStreamIndex = i;
                break;
            }
        }

        AVCodecParameters params = formatContext.streams(audioStreamIndex).codecpar();
        AVCodec codec = avcodec_find_decoder(params.codec_id());
        codecContext = avcodec_alloc_context3(codec);
        avcodec_parameters_to_context(codecContext, params);
        avcodec_open2(codecContext, codec, (AVDictionary) null);

        swr = swr_alloc();

        av_opt_set_chlayout(swr, "out_chlayout", AV_CHANNEL_LAYOUT_STEREO, 0);
        av_opt_set_int(swr, "out_sample_rate", codecContext.sample_rate(), 0);
        av_opt_set_sample_fmt(swr, "out_sample_fmt", AV_SAMPLE_FMT_S16, 0);

        av_opt_set_chlayout(swr, "in_chlayout", codecContext.ch_layout(), 0);
        av_opt_set_int(swr, "in_sample_rate", codecContext.sample_rate(), 0);
        av_opt_set_sample_fmt(swr, "in_sample_fmt", codecContext.sample_fmt(), 0);

        swr_init(swr);

        frame = av_frame_alloc();
        packet = av_packet_alloc();
    }

    @Override
    public ByteBuffer getNextBuffer() {
        if (finished) {
            return null;
        }

        while (av_read_frame(formatContext, packet) >= 0) {
            if (packet.stream_index() != audioStreamIndex) {
                av_packet_unref(packet);
                continue;
            }

            avcodec_send_packet(codecContext, packet);
            av_packet_unref(packet);

            while (avcodec_receive_frame(codecContext, frame) == 0) {

                int outSamples = (int) av_rescale_rnd(
                        swr_get_delay(swr, codecContext.sample_rate()) + frame.nb_samples(),
                        codecContext.sample_rate(),
                        codecContext.sample_rate(),
                        AV_ROUND_UP
                );

                int bufferSize = outSamples * 2 * 2;

                if (reuseBuffer == null || reuseBuffer.capacity() < bufferSize) {
                    if (reuseBuffer != null) {
                        reuseBuffer.deallocate();
                    }
                    reuseBuffer = new BytePointer(bufferSize);
                }

                PointerPointer<BytePointer> out = new PointerPointer<>(1);
                out.put(0, reuseBuffer);

                int samples = swr_convert(
                        swr,
                        out,
                        outSamples,
                        frame.data(),
                        frame.nb_samples()
                );

                if (samples > 0) {
                    int bytes = samples * 2 * 2;

                    ByteBuffer src = reuseBuffer.asBuffer();
                    src.limit(bytes);

                    ByteBuffer copy = ByteBuffer.allocateDirect(bytes);
                    copy.put(src);
                    copy.flip();

                    return copy;
                }
            }
        }

        finished = true;
        return null;
    }

    public void close() {
        if (reuseBuffer != null) {
            reuseBuffer.deallocate();
        }

        swr_free(swr);
        av_frame_free(frame);
        av_packet_free(packet);
        avcodec_free_context(codecContext);
        avformat_close_input(formatContext);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    public int getSampleRate() {
        return codecContext.sample_rate();
    }

    public int getChannels() {
        return codecContext.ch_layout().nb_channels();
    }

    public int getFormat() {
        return codecContext.sample_fmt();
    }
}