DESCRIPTION = "Simple and clean multi resolution skin SimpleGray by Taapat"
MAINTAINER = "Taapat"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

inherit gittag allarch python3-compileall

PV = "git"
PKGV = "${GITPKGVTAG}"

SRC_URI = "git://github.com/Taapat/skin-SimpleGray.git;protocol=https;branch=master"

FILES:${PN} = "${prefix}/"

S = "${WORKDIR}/git"

do_compile() {
	python3 -O -m compileall ${S}${libdir}/enigma2/python/Components/
}

do_install() {
	install -d ${D}${prefix}
	cp -r ${S}${prefix}/* ${D}${prefix}/
}
