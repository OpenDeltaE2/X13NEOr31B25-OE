SUMMARY = "Open Source Qt WebEngine HbbTV Browser Extension"
PACKAGE_ARCH := "${MACHINE_ARCH}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = " \
file://__init__.py;md5=d41d8cd98f00b204e9800998ecf8427e \
"

RDEPENDS:${PN} = "openhbbtvbrowser"

inherit python3-compileall

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git"
PKGV = "1.0+git${GITPKGV}"
PR = "r1"

SRC_URI = "git://github.com/openhbbtvbrowser/enigma2-plugin-extensions-openhbbtvbrowser.git;protocol=https;branch=master \
    ${@bb.utils.contains_any("MACHINE_FEATURES", "mali", "file://eglfs.patch", "", d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'vu-eglfs', 'file://0001-add-vuplus-support.patch' , '', d)} \
"

S = "${WORKDIR}/git"

do_install(){
    install -d ${D}${libdir}/enigma2/python/Plugins/Extensions/HbbTV
    install -m 0755 ${S}/*.py ${D}${libdir}/enigma2/python/Plugins/Extensions/HbbTV
}

FILES:${PN} = "${libdir}"
