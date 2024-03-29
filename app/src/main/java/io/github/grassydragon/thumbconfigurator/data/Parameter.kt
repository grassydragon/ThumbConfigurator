package io.github.grassydragon.thumbconfigurator.data

enum class Parameter(
    val id: String,
    val options: List<String>,
    val values: List<Int>,
    val default: Int,
    val note: String = ""
) {

    VIDEO_RESOLUTION(
        "videoResolution",
        listOf("1080p60fps", "1080p50fps", "1080p30fps"),
        (0..2).toList(),
        0
    ),

    GYRO_DATA(
        "GCsv",
        listOf("OFF", "ON"),
        listOf(0, 1),
        0,
        "Works only with the 1080p50fps resolution"
    ),

    LOOP_RECORDING_INTERVAL(
        "LoopRecordingInterval",
        listOf("OFF", "1 minute", "3 minutes", "5 minutes"),
        (0..3).toList(),
        0
    ),

    AUTO_RECORDING(
        "AutoRecording",
        listOf("OFF", "ON"),
        listOf(0, 1),
        0
    ),

    EXPOSURE(
        "Exposure",
        (0..12).map { it.toString() },
        (0..12).toList(),
        6
    ),

    BRIGHTNESS(
        "Brightness",
        (0..10).map { it.toString() },
        (0..10).toList(),
        5
    ),

    SHARPNESS(
        "Sharpness",
        listOf("Lower", "Low", "Default", "High", "Higher"),
        (1..5).toList(),
        3
    ),

    CONTRAST(
        "Contrast",
        listOf("Lower", "Low", "Default", "High", "Higher"),
        (1..5).toList(),
        3
    ),

    SATURATION(
        "Saturation",
        listOf("Lower", "Low", "Default", "High", "Higher"),
        (1..5).toList(),
        3
    ),

    WHITE_BALANCE(
        "WhiteBalance",
        listOf("Auto", "Daylight", "Cloudy", "Tungsten"),
        (1..4).toList(),
        1
    ),

    POWER_SAVING_MODE(
        "PowerSavingMode",
        listOf("OFF", "1 minute", "3 minutes"),
        listOf(0, 1, 3),
        0
    );

    val displayName = name
        .lowercase()
        .replace('_', ' ')
        .replaceFirstChar { it.uppercaseChar() }

    fun getOption(value: Int): String {
        return options[values.indexOf(value)]
    }

    fun getValue(option: String): Int {
        return values[options.indexOf(option)]
    }

}
