SUMMARY = "Stalker for QT browser"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"

inherit gitpkgv autotools pkgconfig python3-compileall

PV = "1.0+git"
PKGV = "1.0+git${GITPKGV}"
VER ?= "${@bb.utils.contains('MACHINE_FEATURES', 'hisil', '-v2', '', d)}"

SRC_URI = "git://github.com/zgemma-star/e2plugins.git;protocol=https;branch=python3"

PACKAGES = "${PN}"
RDEPENDS:${PN}  = "qtwebkit python3-netifaces" 

S = "${WORKDIR}/git/qtstalker${VER}"

QtStalker = "enigma2/python/Plugins/Extensions/Stalker"

do_compile () {
}

FILES:${PN} =  " \
	${bindir} \
	${libdir}/${QtStalker} \
	${datadir}/stalker \
"

do_install() {
	install -d ${D}${libdir}/${QtStalker}
	install -d ${D}${datadir}/stalker
	cp --preserve=mode,timestamps -r ${S}${datadir}/stalker/* ${D}${datadir}/stalker/
	chmod -R a+rX ${D}${datadir}/stalker/
	install -m 0755 ${S}/plugin/__init__.py ${D}${libdir}/${QtStalker}
	install -m 0755 ${S}/plugin/browser.py ${D}${libdir}/${QtStalker}
	install -m 0755 ${S}/plugin/datasocket.py ${D}${libdir}/${QtStalker}
	install -m 0755 ${S}/plugin/plugin.py ${D}${libdir}/${QtStalker}
	install -m 0755 ${S}/plugin/stalker.py ${D}${libdir}/${QtStalker}
	install -m 0755 ${S}/plugin/*.png ${D}${libdir}/${QtStalker}
	install -d ${D}/${bindir}
	install -m 0755 ${S}/stalker* ${D}${bindir}
}

do_package_qa() {
}
PACKAGE_ARCH := "${MACHINE_ARCH}"

# prevent 'double stripping' our binaries, which will break them
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

INSANE_SKIP:${PN} += "already-stripped"
