SUMMARY = "Multi boot loader for enigma2"
MAINTAINER = "oe-alliance"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

inherit gitpkgv

PV = "1.0+git"
PKGV = "1.0+git${GITPKGV}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS = "freetype json-c"

SRC_URI = "git://github.com/oe-alliance/openmultiboot.git;protocol=https;branch=main"

inherit autotools-brokensep pkgconfig

S = "${WORKDIR}/git"

EXTRA_OEMAKE = " \
    'CFLAGS=${CFLAGS} \
    -I=${includedir}/freetype2 \
    ${@bb.utils.contains("MACHINE_FEATURES", "singlecore", "-DOMB_DEFAULT_TIMER=10" , "-DOMB_DEFAULT_TIMER=5", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "textlcd", "-DOMB_HAVE_TEXTLCD" , "", d)} \
    ${@bb.utils.contains("IMAGE_FSTYPES", "ubi", "-DOMB_FLASH_UBI" , "", d)} \
    ${@bb.utils.contains("IMAGE_FSTYPES", "jffs2", "-DOMB_FLASH_JFFS2" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dreambox", "-DOMB_DREAMBOX", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "mmc", "-DOMB_MMCBLK", "", d)} \
    -DOMB_KERNEL_MTD=\"/dev/${MTD_KERNEL}\"' \
    'LDFLAGS= -lfreetype ${LDFLAGS}' \
    "

do_install() {
    install -d ${D}${base_sbindir}
    install -m 755 ${S}/src/open_multiboot ${D}${base_sbindir}
}

pkg_preinst:${PN}() {
#!/bin/sh
if mountpoint -q ${libdir}/enigma2/python/Plugins/Extensions/OpenMultiboot; then
    echo "openMultiBoot will only install on main image."
    echo "Child image is running - canceling installation!"
    sleep 3
    exit 1
else
    echo "Main image is running - proceeding installation..."
    exit 0
fi
}

pkg_postrm:${PN}() {
#!/bin/sh
rm -rf /sbin/init
ln -s /sbin/init.sysvinit /sbin/init
rm -rf /sbin/open-multiboot-branding-helper.py
exit 0
}
