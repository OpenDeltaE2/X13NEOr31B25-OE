DESCRIPTION = "This program converts .m3u8 playlists to a .ts video. It supports decryption of both AES-128 and SAMPLE-AES encryption."
MAINTAINER = "selsta"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ec892df81d7a9e0b5cd420bb5603c288"

DEPENDS = "curl openssl zlib ffmpeg"

inherit gitpkgv

PV = "1.0+git"
PKGV = "1.0+git${GITPKGV}"

SRC_URI = "git://github.com/selsta/hlsdl.git;protocol=git;protocol=https;branch=master"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

SOURCE_FILES = "src/main.c"
SOURCE_FILES =+ "src/aes_openssl.c"
SOURCE_FILES =+ "src/curl.c"
SOURCE_FILES =+ "src/hls.c"
SOURCE_FILES =+ "src/misc.c"
SOURCE_FILES =+ "src/msg.c"
SOURCE_FILES =+ "src/mpegts.c"

do_compile() {
    ${CC} ${SOURCE_FILES} -D_FILE_OFFSET_BITS=64 -D_LARGEFILE64_SOURCE -D_LARGEFILE_SOURCE -D_GNU_SOURCE=1 -DWITH_FFMPEG -Wall -Wstrict-prototypes -Wmissing-prototypes -Wmissing-declarations -Wno-deprecated-declarations -Wshadow -Wpointer-arith -Wcast-qual -Wsign-compare -DPREFIX="/usr" -std=gnu11 -I${S}/src -I${D}/${libdir} -I${D}/${includedir} -lrt -lpthread -lz -lssl -lcrypto -lcurl -lavcodec -lavformat -lavutil -o hlsdl ${LDFLAGS}
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/hlsdl ${D}${bindir}/
}

FILES:${PN} = "${bindir}"
