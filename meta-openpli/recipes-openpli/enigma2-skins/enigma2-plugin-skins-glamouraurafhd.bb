DESCRIPTION = "Glamour Aura FHD skin for new generation STBs with OpenPLI based images"
MAINTAINER = "MCelliotG"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=84dcc94da3adb52b53ae4fa38fe49e5d"

inherit gitpkgv allarch python3-compileall

PV = "6.0.5+git"
PKGV = "6.0.5+git${GITPKGV}"

SRC_URI = "git://github.com/MCelliotG/GlamourAuraFHD-skin.git;protocol=https;branch=master"

FILES:${PN} = "/usr"

S = "${WORKDIR}/git"

do_compile() {
}

do_install() {
	install -d ${D}/usr
	cp -r --preserve=mode,links ${S}/usr/* ${D}/usr/
	chmod -R a+rX ${D}/usr
}
