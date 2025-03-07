SUMMARY = "IPTV Xtream Codes/XUI One playlists player by KiddaC"
HOMEPAGE = "https://www.linuxsat-support.com"
MAINTAINER = "kiddac"
require conf/license/license-gplv2.inc

RDEPENDS:${PN} = "python3 python3-multiprocessing python3-requests \
                  python3-image python3-imaging python3-pillow wget \
"

inherit gitpkgv allarch python3-compileall

SRCREV = "${AUTOREV}"

PV = "git"
PKGV = "${GITPKGV}"

SRC_URI = "git://github.com/kiddac/XKlass.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

FILES:${PN} = " ${libdir}/enigma2/python/Components/Converter/* \
                ${libdir}/enigma2/python/Components/Renderer/* \
                ${libdir}/enigma2/python/Plugins/Extensions/XKlass/*"

do_patch[noexec] = "1"

do_configure[noexec] = "1"

do_compile[noexec] = "1"

do_install() {
install -d ${D}${libdir}/enigma2/python/Components/Converter
install -d ${D}${libdir}/enigma2/python/Components/Renderer
install -d ${D}${libdir}/enigma2/python/Plugins/Extensions/XKlass
cp -rf ${S}/XKlass/usr/lib/enigma2/python/Components/Converter/*.py ${D}${libdir}/enigma2/python/Components/Converter/
cp -rf ${S}/XKlass/usr/lib/enigma2/python/Components/Renderer/*.py ${D}${libdir}/enigma2/python/Components/Renderer/
cp -rf ${S}/XKlass/usr/lib/enigma2/python/Plugins/Extensions/XKlass/* ${D}${libdir}/enigma2/python/Plugins/Extensions/XKlass/
}

