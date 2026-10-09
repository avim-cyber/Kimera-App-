package com.example.model

enum class ConsultationTab(val label: String, val iconName: String) {
    SESSION("Session", "chair"),
    STUDIO("Studio", "auto_awesome"),
    FORMULA("Formula", "history_edu")
}

data class StyleVariation(
    val id: String,
    val title: String,
    val subtitle: String,
    val profileFit: String,
    val maintenanceLevel: String,
    val matchPercentage: Int,
    val description: String,
    val tags: List<TagItem>,
    val imageUrl: String,
    val referenceUrl: String,
    val isRecommended: Boolean = false,
    val upkeepPeriod: String = "3-4 wks"
)

data class TagItem(
    val icon: String,
    val label: String
)

enum class MatchFidelity(
    val title: String,
    val subtitle: String,
    val iconName: String
) {
    EXACT(
        title = "Exact Match / Exceeded",
        subtitle = "Fringe length, taper graduation, and styling density mirrored the render flawlessly.",
        iconName = "check"
    ),
    CLOSE(
        title = "Close Match (Minor Differences)",
        subtitle = "Subtle silhouette variation due to natural cowlick or hair density calibration.",
        iconName = "thumb_up"
    ),
    DIFFERENT(
        title = "Different from Preview",
        subtitle = "Stylist and client pivoted direction during scissor-over-comb phase.",
        iconName = "tune"
    )
}

data class AccuracyAnchor(
    val id: String,
    val label: String,
    val isSelected: Boolean
)

object ConsultationData {
    const val KIMERA_LOGO_URL =
        "https://lh3.googleusercontent.com/aida-public/AB6AXuB3J1r4Agr22tpW9Gjpw5g0RMPoeX8-LGqxq2d1wkXgyRUqsBGVUy7BYJA_lyA-wDVe1XkoEnxJx_5ZkXEq912_4mfMkVvrOTGHFnAthvIx6x3I17EUTuYEX7lBUPSxwfurKeX5w2Fzgsf_du0HeNmGUQ-sHnAVFlzQiTGRLvFnuEcZtBHN1INrRSCc8dcx2VycVLtmJ0tYUuXCCXU3Wka-GD8HmOmHkIbasEqhUmPu9iUBahPAWvfB4eYrpYcqVDU-VKE"

    const val VARIATION_A_URL =
        "https://lh3.googleusercontent.com/aida-public/AB6AXuDfxx0wHY_F3FjEB25sTCsCamj8tBDs7b91LezSUByimuIqv725vojOfAj6ssP1piTqMEjqgRtQnnyY1Wra0sOlzfH1uWS3hgyS6s-uQSklAvi3qDRssePxy2a-VbfXv-_dqajarO6tdqQZiUX491i5T2i7qKuqGsmfpV1OnLgtSVW9jXGDjSXiP7jbolpknf45XnM4J_J886ka6OP85bfiTKfWiEz8C9SBCwu1NyfgQ36ETh7p3BZYXA"

    const val LIVE_MIRROR_URL =
        "https://lh3.googleusercontent.com/aida-public/AB6AXuDfl4oOqrHlsUJQpO6rQ9P_ckqW1bahMX3pbzfXnReeco04HJb9uoEHq382zaZJbqIBh3auMFWGnUsjTHgny-Jfrqbf6-8n8o5shx392aOG3xKhkW77H_B56elGv2D10WBHCBsfRIk2YxVu24wh5rkZUuIklagJmXmahb2hSjhCc2XiBDbniyYde2s9_z4EaL3dxSNXK60bBQbVQ4GJcNBsDHdKVJYyWR1xHm4DQ-f42oLzYlQYCdqbmQ"

    // High quality reference portrait URL
    const val PINTEREST_REFERENCE_URL = VARIATION_A_URL

    val variations = listOf(
        StyleVariation(
            id = "A",
            title = "Low Drop Fade & Crop",
            subtitle = "Variation A • Low Drop Fade",
            profileFit = "Optimal for Oval Profile",
            maintenanceLevel = "Low Maintenance",
            matchPercentage = 98,
            description = "Soft blurred taper contouring behind the ears. Maximum natural top movement with weight taken out for humid Bengaluru climate.",
            tags = listOf(
                TagItem("content_cut", "Feathered fringe"),
                TagItem("water_drop", "Matte sea salt finish"),
                TagItem("straighten", "1.5 Guard drop")
            ),
            imageUrl = VARIATION_A_URL,
            referenceUrl = PINTEREST_REFERENCE_URL,
            isRecommended = true,
            upkeepPeriod = "3-4 wks"
        ),
        StyleVariation(
            id = "B",
            title = "Mid Skin Fade & Crop",
            subtitle = "Variation B • Mid Skin Fade",
            profileFit = "Sharper Angles",
            maintenanceLevel = "Biarritz Edge",
            matchPercentage = 93,
            description = "Crisp temple drop with high skin blend. Emphasizes cheekbone definition with a structured horizontal blunt fringe.",
            tags = listOf(
                TagItem("brush", "Crisp temple lines"),
                TagItem("schedule", "2-3 wk upkeep")
            ),
            imageUrl = LIVE_MIRROR_URL,
            referenceUrl = PINTEREST_REFERENCE_URL,
            isRecommended = false,
            upkeepPeriod = "2-3 wks"
        ),
        StyleVariation(
            id = "C",
            title = "Natural Taper & Scissor Cut",
            subtitle = "Variation C • Scissor Taper",
            profileFit = "Classic Organic",
            maintenanceLevel = "Gentle Flow",
            matchPercentage = 89,
            description = "Fuller side volume with all-scissor graduation. Gentle soft tapering at the neckline for relaxed, effortless grow-out.",
            tags = listOf(
                TagItem("spa", "Zero clippers used"),
                TagItem("calendar_month", "6-8 wk upkeep")
            ),
            imageUrl = VARIATION_A_URL,
            referenceUrl = PINTEREST_REFERENCE_URL,
            isRecommended = false,
            upkeepPeriod = "6-8 wks"
        )
    )

    val initialAccuracyAnchors = listOf(
        AccuracyAnchor("hairline", "Accurate hairline framing", true),
        AccuracyAnchor("fade", "Right fade length", true),
        AccuracyAnchor("texture", "Texture matched reference", true),
        AccuracyAnchor("crown", "Crown blend realism", false),
        AccuracyAnchor("parting", "Parting precision", false)
    )
}
