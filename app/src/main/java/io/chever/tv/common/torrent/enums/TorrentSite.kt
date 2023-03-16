package io.chever.tv.common.torrent.enums

/**
 * TODO: document enum class
 */
enum class TorrentSite(val url: String) {

    @Deprecated("Required captcha resolution.")
    Sitorrent("https://sitorrent.co/buscar/"),

    @Deprecated("Required captcha resolution.")
    MiTorrent("https://mitorrent.org/"),

    /**
     * TorrentLatino only ES.
     * @see
     * <a href="https://ww5.cinecalidad.link/">https://ww5.cinecalidad.link/</a>
     */
    CineCalidad("https://cinecalidad.run/"),

    /**
     * Torrents EN/ES.
     * @see
     * <a href="https://torrentgalaxy.to/">https://torrentgalaxy.to/</a>
     */
    TorrentGalaxy("https://torrentgalaxy.to/torrents.php?c3=1&c42=1&c1=1&nox=2&nox=1"),

    /**
     * HackTorrent dual EN/ES.
     * @see
     * <a href="https://hacktorrent.org">https://hacktorrent.org</a>
     */
    @Deprecated("Required captcha resolution.")
    HackTorrent("https://hacktorrent.eu/"),

    // Add more sites ...
}