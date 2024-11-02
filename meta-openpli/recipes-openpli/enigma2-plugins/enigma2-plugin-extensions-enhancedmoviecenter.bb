SUMMARY = "Filemanager MoviePlayer Extentions"
MAINTAINER = "Coolman, Betonme & Swiss-MAD"
SECTION = "extra"
PRIORITY = "optional"
require conf/license/license-gplv2.inc

DEPENDS = "python3-six-native"
RDEPENDS:${PN} = "gstreamer1.0-plugins-good-flv gstreamer1.0-plugins-bad-rtmp python3-json python3-html python3-requests python3-mutagen rtmpdump python3-chardet python3-tmdbsimple"

inherit gitpkgv autotools-brokensep gettext python3-compileall

SRCREV = "${AUTOREV}"
PV = "4.0.+git"
PKGV = "4.0.+git${GITPKGV}"

SRC_URI="git://github.com/oe-mirrors/EnhancedMovieCenter.git;branch=master;protocol=https \
         file://set-shebang-to-python3.patch \
"

S = "${WORKDIR}/git"

EXTRA_OECONF = " \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
"

PARALLEL_MAKEINST = ""

CONFFILES:${PN} = "${sysconfdir}/enigma2/emc-hide.cfg ${sysconfdir}/enigma2/emc-noscan.cfg ${sysconfdir}/enigma2/emc-permsort.cfg ${sysconfdir}/enigma2/emc-topdir.cfg"

PACKAGES =+ "${PN}-po"
FILES:${PN} = "${sysconfdir} ${libdir}"

FILES:${PN}-po = "${libdir}/enigma2/python/Plugins/Extensions/EnhancedMovieCenter/locale/*/*/*.po"

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"

pkg_postinst:${PN}() {
#!/bin/sh
echo ""
echo ""
echo "***********************************"
echo "*     Enhanced Movie Center       *"
echo "*             V 4.0               *"
echo "*               by                *"
echo "*   Coolman, Betonme & Swiss-MAD  *"
echo "***********************************"
echo ""
echo ""
echo "Plugin successfully installed!"
echo ""
echo "You should restart enigma2 now..."
echo ""
echo ""
echo ""
exit 0
}

pkg_postrm:${PN}() {
#!/bin/sh
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/EnhancedMovieCenter/
echo "Plugin removed! You should restart enigma2 now!"
exit 0
}
