SUMMARY = "Enigma2 plugin to manage your youtube account and watch videos"
DESCRIPTION = "Small plugin to manage your account, search and watch videos \
from youtube"
HOMEPAGE = "https://github.com/Taapat/enigma2-plugin-youtube"
SECTION = "multimedia"
LICENSE = "PD"
LIC_FILES_CHKSUM = "file://COPYING.GPLv2;md5=b234ee4d69f5fce4486a80fdaf4a4263"
SRC_URI = "git://github.com/Taapat/enigma2-plugin-youtube.git;protocol=https;branch=master"
S = "${WORKDIR}/git"

inherit gitpkgv python3-compileall
PV = "1+git"
PKGV = "1+git${GITPKGV}"

inherit setuptools3-openplugins

RDEPENDS:${PN} = " \
	python3-core \
	python3-codecs \
	python3-json \
	python3-netclient \
	python3-email \
	python3-datetime \
	python3-threading \
	"
