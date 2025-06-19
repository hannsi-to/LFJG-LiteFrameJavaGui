package me.hannsi.lfjg.utils.type.types;

import lombok.Getter;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

// Reference: https://x.com/n_seitan
public enum Theme implements IEnumTypeBase {
    CALMING_MUTED_GREEN(0, "CalmingMutedGreen", Color.of(0x405a5c), Color.of(0xf0efe6), Color.of(0x5f7e7b), Color.of(0xe3c1af)),
    LUXURIOUS_PINK(1, "LuxuriousPink", Color.of(0xd480a4), Color.of(0xf3e4e7), Color.of(0xe1b16d), Color.of(0x4e3535)),
    COLD_BLUE_PURPLE(2, "ColdBluePurple", Color.of(0x4e4993), Color.of(0xbfc9cb), Color.of(0xf4f5f1), Color.of(0xa1cdc4)),
    CALMING_RED(3, "CalmingRed", Color.of(0xa83d49), Color.of(0x9a9c97), Color.of(0x4c4951), Color.of(0xeee7e9)),
    CUTE_YET_COOL_PINK_BLUE(4, "CuteYetCoolPinkBlue", Color.of(0xe2788c), Color.of(0xf1f2f7), Color.of(0x83adf0), Color.of(0xedd0ca)),
    SOPHISTICATED_LIGHT_GRAY_AND_DEEP_BLUE(5, "SophisticatedLightGrayAndDeepBlue", Color.of(0x1f3555), Color.of(0x515561), Color.of(0x242740), Color.of(0xeeebcc)),
    WARM_YELLOW_GREEN_AND_WHITE_AND_GRAY(6, "WarmYellow_GreenAndWhiteAndGray", Color.of(0x272c1a), Color.of(0xfffeef), Color.of(0x446158), Color.of(0x9fd700)),
    DAWN_GRADIENT(7, "DawnGradient", Color.of(0x63517d), Color.of(0x8d5f8c), Color.of(0xc29899), Color.of(0xe0bdad)),
    TOXIC_LOOKING_PURPLE_GREEN(8, "ToxicLookingPurpleGreen", Color.of(0x106249), Color.of(0x579f82), Color.of(0x9c227c), Color.of(0x5c1f64)),
    POPPY_CHOCOLATE_MINT(9, "PoppyChocolateMint", Color.of(0xc66684), Color.of(0xfffeef), Color.of(0x7e412a), Color.of(0x8ad0bb)),
    GENTLE_MOSS_GREEN(10, "GentleMossGreen", Color.of(0xa4e3a6), Color.of(0xe4e4d7), Color.of(0xa4afa6), Color.of(0xe4e7e1)),
    CALMING_BEIGE_BROWN(11, "CalmingBeigeBrown", Color.of(0x514935), Color.of(0x907e6e), Color.of(0xdad0c7), Color.of(0xede8e3)),
    WELL_BALANCED_PURPLE_AND_WHITE(12, "WellBalancedPurpleAndWhite", Color.of(0x402d5d), Color.of(0x49465e), Color.of(0x6e4377), Color.of(0xf1f2ed)),
    REFRESHING_SKY_BLUE(13, "RefreshingSkyBlue", Color.of(0x151e4b), Color.of(0x5a7a9a), Color.of(0x69bfef), Color.of(0xeff0f0)),
    GENTLE_WHITE_CHOCOLATE(14, "GentleWhiteChocolate", Color.of(0xe7ab88), Color.of(0xf0e6cc), Color.of(0xf3e2b5), Color.of(0xf4eedd)),
    ELEGANT_RED_EMERALD(15, "ElegantRedEmerald", Color.of(0xad1f4e), Color.of(0x2b3f52), Color.of(0xe5e8f1), Color.of(0x4ca380)),
    ETHNIC_BROWN(16, "EthnicBrown", Color.of(0x374f43), Color.of(0xe6bda1), Color.of(0xb86952), Color.of(0xd3b8af)),
    CALMING_GRAY_TO_ORANGE(17, "CalmingGrayToOrange", Color.of(0xde721e), Color.of(0x8a929c), Color.of(0xafb1c4), Color.of(0x332732)),
    DARK_GRAY_PURPLE(18, "DarkGrayPurple", Color.of(0x8400db), Color.of(0x353650), Color.of(0x24272d), Color.of(0x24212f)),
    DREAMY_PINK_BLUE(19, "DreamyPinkBlue", Color.of(0x005397), Color.of(0xdde8ed), Color.of(0xdfa0bd), Color.of(0xb7dee3)),
    REFRESHING_CHIC_YELLOW(20, "RefreshingChicYellow", Color.of(0xa0b2c6), Color.of(0xdcdeec), Color.of(0xede589), Color.of(0xf4d702)),
    RETRO_CUTE_PINK_BLUE(21, "RetroCutePinkBlue", Color.of(0x70e0ef), Color.of(0x333048), Color.of(0xebede6), Color.of(0xf0868b)),
    PLEASANT_OCEAN_BLUE(22, "PleasantOceanBlue", Color.of(0xfbc8b9), Color.of(0x82d6ee), Color.of(0x0b379c), Color.of(0x061773)),
    VIVID_TRICOLOR(23, "VividTricolor", Color.of(0xe52a5d), Color.of(0xffd72a), Color.of(0x182641), Color.of(0x111b2e)),
    SOPHISTICATED_BOLD_PINK(24, "SophisticatedBoldPink", Color.of(0xd34669), Color.of(0xe1c8d2), Color.of(0xe16f94), Color.of(0xfbf3f3)),
    DANDY_BLUE_COLOR_SCHEME(25, "DandyBlueColorScheme", Color.of(0x896f3d), Color.of(0x404751), Color.of(0x1a293f), Color.of(0x102134)),
    ELEGANT_BEIGE_GREEN(26, "ElegantBeigeGreen", Color.of(0x70a284), Color.of(0xbda4a1), Color.of(0xdfd8cd), Color.of(0xf5ebe3)),
    TRANSLUCENT_BLUE_PURPLE(27, "TranslucentBluePurple", Color.of(0x837ae6), Color.of(0x4d5254), Color.of(0xbf6fe3), Color.of(0xede9e8)),
    TRENDY_RED_AND_WHITE(28, "TrendyRedAndWhite", Color.of(0xed2e3d), Color.of(0xd9d5cf), Color.of(0x2d211c), Color.of(0xf5f5f5)),
    PURE_BLUE_GRADIENT(29, "PureBlueGradient", Color.of(0x2a2e70), Color.of(0x555269), Color.of(0xdad1de), Color.of(0x4c68c0)),
    STYLISH_RETRO_ORANGE(30, "StylishRetroOrange", Color.of(0xf49340), Color.of(0xfbdcaf), Color.of(0x373735), Color.of(0xeae8e1)),
    GRAY_THAT_HIGHLIGHTS_NEON_COLORS(31, "GrayThatHighlightsNeonColors", Color.of(0x27e9b5), Color.of(0x3b5265), Color.of(0x162936), Color.of(0x051824)),
    CALMING_KHAKI_GREEN(32, "CalmingKhakiGreen", Color.of(0x3f4640), Color.of(0x3d5a55), Color.of(0xe8e7dd), Color.of(0x7e7d69)),
    LOVELY_PINK_BROWN(33, "LovelyPinkBrown", Color.of(0x403020), Color.of(0xf5c4c8), Color.of(0x976653), Color.of(0xf2a7a3)),
    CUTE_GREEN_PINK(34, "CuteGreenPink", Color.of(0xde9489), Color.of(0xe3c7af), Color.of(0x577051), Color.of(0xdde2e3)),
    TROPICAL_ORANGE_AND_LIGHT_BLUE(35, "TropicalOrangeAndLightBlue", Color.of(0xf58b05), Color.of(0xffc22f), Color.of(0x7fbbdd), Color.of(0xc4e9f2)),
    STYLISH_MUTED_GREEN_BEIGE(36, "StylishMutedGreenBeige", Color.of(0x8a9174), Color.of(0xc9a898), Color.of(0xc6925d), Color.of(0xdfdcd6)),
    GORGEOUS_WHITE_GOLD(37, "GorgeousWhiteGold", Color.of(0xd6a90d), Color.of(0xf5f1d5), Color.of(0xa86f02), Color.of(0xf2f2f2)),
    CHIC_MUTED_BLUE(38, "ChicMutedBlue", Color.of(0x3d547f), Color.of(0xf3f1eb), Color.of(0xcecbd3), Color.of(0xb2c0d2)),
    MUTED_ICE_CREAM_COLOR(39, "MutedIceCreamColor", Color.of(0xf5b5c6), Color.of(0xe3e3e1), Color.of(0xe3d5bb), Color.of(0xc1d3d0)),
    CLEAR_SKY_BLUE(40, "ClearSkyBlue", Color.of(0x4981cf), Color.of(0xcadaee), Color.of(0x89aad3), Color.of(0xe8edf2)),
    SOFT_CHERRY_BLOSSOM_GRADIANT(41, "SoftCherryBlossomGradiant", Color.of(0xab6786), Color.of(0xed96b3), Color.of(0xdd7594), Color.of(0xeab1c6)),
    EMOTIONAL_PINK(42, "EmotionalPink", Color.of(0xeca6b7), Color.of(0xb5bfd4), Color.of(0xe8bbcf), Color.of(0xfededf)),
    FRUITY_PINK(43, "FruityPink", Color.of(0x82d415), Color.of(0x1a283c), Color.of(0xff546b), Color.of(0x82d415)),
    CHIC_BEIGE(44, "ChicBeige", Color.of(0xa68076), Color.of(0xeac4af), Color.of(0xefc3c2), Color.of(0xf7e6d6)),
    DREAMY_PURPLE_PINK(45, "DreamyPurplePink", Color.of(0xfbd3da), Color.of(0xa1a9c0), Color.of(0xa7aedb), Color.of(0xc7d4de)),
    GRACEFUL_WHITE_GOLD(46, "GracefulWhiteGold", Color.of(0xd7b84a), Color.of(0xecdf96), Color.of(0xd5d7d7), Color.of(0xeaedf0)),
    NATURAL_WHITE_AND_GREEN(47, "NaturalWhiteAndGreen", Color.of(0x455a4f), Color.of(0xd9dcd6), Color.of(0x6e8682), Color.of(0xe7e8e3)),
    VIBRANT_GREEN_PINK(48, "VibrantGreenPink", Color.of(0xed4280), Color.of(0xe1f7cc), Color.of(0x327b41), Color.of(0x2e4626)),
    BLUE_PINK_THAT_STANDS_OUT_AGAINST_BLACK(49, "BluePinkThatStandsOutAgainstBlack", Color.of(0xfe0369), Color.of(0x0585e6), Color.of(0x0236a5), Color.of(0x091221)),
    VIVID_RED(50, "VividRed", Color.of(0xdf0139), Color.of(0x28242), Color.of(0xe2e2e2), Color.of(0x1e1e27));

    final int id;
    final String name;
    @Getter
    final Color mainColor;
    @Getter
    final Color subColor1;
    @Getter
    final Color subColor2;
    @Getter
    final Color subColor3;

    Theme(int id, String name, Color mainColor, Color subColor1, Color subColor2, Color subColor3) {
        this.id = id;
        this.name = name;
        this.mainColor = mainColor;
        this.subColor1 = subColor1;
        this.subColor2 = subColor2;
        this.subColor3 = subColor3;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

}
