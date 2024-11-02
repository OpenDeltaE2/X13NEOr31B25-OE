DESCRIPTION = "streamlinksrv is a helper deamon for streamlink"
SECTION = "devel/python"
MAINTAINER = "SatDreamGR Billy2011"
HOMEPAGE = "www.satdreamgr.com"
LICENSE = "GPL-2.0-or-later"
require conf/license/license-gplv2.inc

inherit allarch gittag

RDEPENDS:${PN} = "python3-core streamlink"

SRC_URI = "git://github.com/oe-mirrors/livestreamersrv;protocol=https;branch=streamlinksrv \
           file://set-interpreter-to-python3.patch \
"

S = "${WORKDIR}/git"

PV = "6.11.0+git"
PKGV = "6.11.0+${GITPKGVTAG}"

do_install:append() {
    install -d ${D}${sbindir}
    install -d ${D}${datadir}
    install -d ${D}${sysconfdir}/init.d
    install -d ${D}${sysconfdir}/rc3.d
    install -d ${D}${sysconfdir}/rc4.d
    install -m 0755 ${S}/streamlinksrv ${D}${sbindir}
    install -m 0644 ${S}/offline.mp4 ${D}${datadir}
    ln -sf ${sbindir}/streamlinksrv ${D}${sysconfdir}/init.d/streamlinksrv
    ln -sf ../init.d/streamlinksrv ${D}${sysconfdir}/rc3.d/S50streamlinksrv
    ln -sf ../init.d/streamlinksrv ${D}${sysconfdir}/rc4.d/S50streamlinksrv
}

FILES:${PN} = "/"

do_package_qa[noexec] = "1"
