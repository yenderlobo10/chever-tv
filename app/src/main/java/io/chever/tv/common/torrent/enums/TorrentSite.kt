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
     * TorrentPelis only ES.
     * @see
     * <a href="https://torrentpelis.com/">https://torrentpelis.com/</a>
     */
    TorrentPelis("https://torrentpelis.com/"),

    /**
     * Torrents EN/ES.
     * @see
     * <a href="https://torrentgalaxy.to/">https://torrentgalaxy.to/</a>
     */
    TorrentGalaxy("https://torrentgalaxy.to/torrents.php?c3=1&c42=1&c1=1&nox=2&nox=1"),

    YTSYify("https://yts.mx/browse-movies/{q}/all/all/0/latest/{y}/all"),

    // torrentlatino.cc headers

    // Add more sites ...
}