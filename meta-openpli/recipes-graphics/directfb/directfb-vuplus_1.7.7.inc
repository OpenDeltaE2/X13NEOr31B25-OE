GFXPR ?= ""

GFXPR:vusolo4k = "20180125.r0"

SRC_URI[gfxdriver.vusolo4k.md5sum] = "d885a3e2eee35b9e60a10a76a14640fa"
SRC_URI[gfxdriver.vusolo4k.sha256sum] = "8bd439ca04f7b6cd6e9e1ddb0cb5845fc4b170a33930f6488583f97ceee01d82"

GFXPR:vuultimo4k = "20180125.r1"

SRC_URI[gfxdriver.vuultimo4k.md5sum] = "bc545a50f5f6530b31e87fc16a789ca4"
SRC_URI[gfxdriver.vuultimo4k.sha256sum] = "bcb314b3e1467d25d84058810c1b33faf4a5852248be5d18a589cb1c2811b591"

GFXPR:vuuno4k = "20180125.r0"

SRC_URI[gfxdriver.vuuno4k.md5sum] = "6ed86cf7c5647ebd5e0824055743c403"
SRC_URI[gfxdriver.vuuno4k.sha256sum] = "01d97a3bf079c99ba1d23c5ec4673e044604c5584a541310a5dd40e2c74114c2"

GFXPR:vuuno4kse = "20180125.r0"

SRC_URI[gfxdriver.vuuno4kse.md5sum] = "ff1a7c17a646da7c28f4271785161d81"
SRC_URI[gfxdriver.vuuno4kse.sha256sum] = "49b1d98900ef2a75bbbd07c2f304fd688ae08c4aa8439cadae7bbda5c960166b"

GFXPR:vuzero4k = "20180125.r0"

SRC_URI[gfxdriver.vuzero4k.md5sum] = "661c62263192b59bdaccad0111dbf381"
SRC_URI[gfxdriver.vuzero4k.sha256sum] = "1be55ae35f7eaa5151a94be69a10c9f6d914e72057c52cd4b9a82090f5f9f9b3"

GFXPR:vuduo4k = "20181109.r1"
SRC_URI[gfxdriver.vuduo4k.md5sum] = "12db83b5a9ffb534be5d05b3a9ddcf5b"
SRC_URI[gfxdriver.vuduo4k.sha256sum] = "50eb0bc6ff45236e1ec7970ee93834a98f9fe255adf5ed850a861d0e1e870eaf"

GFXPR:vuduo4kse = "20200319.r1"
SRC_URI[gfxdriver.vuduo4kse.md5sum] = "cfe2e69648d6da8f75e4475d812012d3"
SRC_URI[gfxdriver.vuduo4kse.sha256sum] = "a1ee8bd434a6aff051503dfe9fdfa784fe1e0c3038a8abdf088bd20c7dba2769"

GFX_FILE = "directfb_1.7-7_gfxdriver_${MACHINE}_${GFXPR}.tar.gz"

DEPENDS:append:class-target = " libgles-${MACHINE}"

SRC_URI:append:class-target = " \
	file://vuplus-conf.patch \
	file://vuplus-prevent-VT.patch \
	file://disable_linux_input_patch.diff \
	http://code.vuplus.com/download/release/directfb/${GFX_FILE};name=gfxdriver.${MACHINE} \
"

EXTRA_OECONF:append:class-target = "\
	--with-inputdrivers=linuxinput \
	--disable-devmem \
	--disable-video4linux \
	--without-tools \
"

PRIVATE_LIBS:${PN}:append:class-target = "\
	${@bb.utils.contains("MACHINE_FEATURES", "dvbproxy", "libinit.so", "", d)} \
"

do_install:append:class-target() {
	install -d ${D}/etc
	install -d ${D}/usr/lib/directfb-${RV}/systems
	install -d ${D}/usr/lib/directfb-${RV}/gfxdrivers

	install -m 0644 ${WORKDIR}/directfb-vuplus/etc/directfbrc ${D}/etc/directfbrc
	cp -r --preserve=mode,links ${WORKDIR}/directfb-vuplus/usr/lib/directfb-${RV}/systems/* ${D}/usr/lib/directfb-${RV}/systems/
	rm -f ${D}/usr/lib/directfb-${RV}/systems/libdirectfb_dummy.so
	rm -f ${D}/usr/lib/directfb-${RV}/systems/libdirectfb_fbdev.so
	install -m 0755 ${WORKDIR}/directfb-vuplus/usr/lib/directfb-${RV}/gfxdrivers/* ${D}/usr/lib/directfb-${RV}/gfxdrivers/

	cp -r --preserve=mode,links ${WORKDIR}/directfb-vuplus/usr/lib/libinit* ${D}/usr/lib/
}

do_package_qa() {
}

INSANE_SKIP:${PN}:append:class-target = "dev-so"

FILES:${PN}:append:class-target = "\
	/etc/directfbrc \
	/usr/lib/libinit* \
	/usr/lib/directfb-${RV}/systems/libdirectfb_dvb_proxy_sys* \
	/usr/lib/directfb-${RV}/gfxdrivers/* \
"

FILES:${PN}-dbg:append:class-target = "\
	/usr/lib/.debug/libinit* \
	/usr/lib/directfb-${RV}/systems/.debug/* \
	/usr/lib/directfb-${RV}/gfxdrivers/.debug/* \
"

BBCLASSEXTEND = "native"
