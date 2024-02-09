DESCRIPTION = "Additional plugins for Enigma2"
MAINTAINER = "OpenPLi team <info@openpli.org>"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=8e37f34d0e40d32ea2bc90ee812c9131"

PACKAGES_DYNAMIC = "enigma2-plugin-(?!pli-).*"

# This prevents QA warnings because bitbake cannot see the dependencies
# after parsing the recipe due to the PACKAGES_DYNAMIC stuff. It tells
# the system what to build when installing these into an image.
PACKAGES += "\
	enigma2-plugin-extensions-mosaic \
	enigma2-plugin-extensions-fancontrol2 \
	enigma2-plugin-extensions-bonjour \
	enigma2-plugin-extensions-transmission \
	"
RDEPENDS:enigma2-plugin-extensions-mosaic = "aio-grab"
RDEPENDS:enigma2-plugin-extensions-fancontrol2 = "smartmontools hdparm"
RDEPENDS:enigma2-plugin-extensions-bonjour = "avahi-daemon"
RDEPENDS:enigma2-plugin-systemplugins-satipclient = "satipclient"

RRECOMMENDS:enigma2-plugin-systemplugins-blindscan = "virtual/blindscan-dvbs"
RRECOMMENDS:enigma2-plugin-extensions-transmission = "transmission transmission-client"

PROVIDES += "\
	${@bb.utils.contains("MACHINE_FEATURES", "transcoding","enigma2-plugin-systemplugins-transcodingsetup","",d)} \
"

inherit gitpkgv pkgconfig gettext python3-compileall

PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"

# make the origin overridable from OE config, for local mirroring
SRC_ORIGIN ?= "git://github.com/OpenPLi/${BPN}.git;protocol=https;branch=master"
SRC_URI := "${SRC_ORIGIN};branch=python3 "

EXTRA_OECONF = " \
	BUILD_SYS=${BUILD_SYS} \
	HOST_SYS=${HOST_SYS} \
	STAGING_INCDIR=${STAGING_INCDIR} \
	STAGING_LIBDIR=${STAGING_LIBDIR} \
	--without-debug \
"

# Main package should be empty
FILES:${PN} = ""
# But something makes the packages think they depend on it, so just
# deliver an empty hulk for them.
ALLOW_EMPTY:${PN} = "1"

FILES:enigma2-plugin-extensions-movietagger += "${sysconfdir}/enigma2/movietags"
CONFFILES:enigma2-plugin-extensions-movietagger += "${sysconfdir}/enigma2/movietags"

FILES:enigma2-plugin-extensions-babelzapper += "${sysconfdir}/babelzapper"

FILES:enigma2-plugin-extensions-netcaster += "${sysconfdir}/NETcaster.conf"
CONFFILES:enigma2-plugin-extensions-netcaster += "${sysconfdir}/NETcaster.conf"

FILES:enigma2-plugin-extensions-lcd4linux += "${libdir}/enigma2/python/Components/*"

FILES:${PN}-meta = "${datadir}/meta"
PACKAGES += "${PN}-meta ${PN}-build-dependencies"

CFLAGS += "-I${STAGING_INCDIR}/tirpc"
LDFLAGS += "-ltirpc"
CXXFLAGS = " -std=c++11"

inherit autotools-brokensep

S = "${WORKDIR}/git"

DEPENDS = " \
	${PYTHON_PN}-pyopenssl \
	streamripper \
	${PYTHON_PN}-icalendar \
	${PYTHON_PN}-dateutil \
	${PYTHON_PN}-mutagen \
	${PYTHON_PN}-pyusb \
	${PYTHON_PN}-requests \
	${PYTHON_PN}-simplejson \
	${PYTHON_PN}-six-native \
	${PYTHON_PN}-treq \
	${PYTHON_PN}-twisted \
	${PYTHON_PN}-daap \
	libcddb \
	dpflib \
	dvdbackup \
	libtirpc \
	png-util \
	"

python populate_packages:prepend () {
    enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)

    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', '%s', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.py$', 'enigma2-plugin-%s-src', '%s (source files)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\/.*\.po$', 'enigma2-plugin-%s-po', '%s (translations)', recursive=True, match_path=True, prepend=True)

    def getControlLines(mydir, d, package):
        import os
        try:
            src = open(mydir + package + "/CONTROL/control")
        except:
            bb.note("Failed to get control lines for package '%s'" % (package))
            return
        for line in src:
            full_package = "enigma2-plugin-extensions-" + package
            if line.startswith('Package: '):
                full_package = line[9:]
            elif line.startswith('Depends: '):
                rdepends = []
                for depend in line[9:].split(','):
                    depend = depend.strip()
                    if depend.startswith('enigma2') and not depend.startswith('enigma2-'):
                        pass # Ignore silly depends on enigma2 with all kinds of misspellings
                    else:
                        rdepends.append(depend)
                rdepends = ' '.join(rdepends)
                d.setVar('RDEPENDS:' + full_package, rdepends)
            elif line.startswith('Recommends: '):
                d.setVar('RRECOMMENDS:' + full_package, line[12:])
            elif line.startswith('Description: '):
                d.setVar('DESCRIPTION:' + full_package, line[13:])
            elif line.startswith('Replaces: '):
                d.setVar('RREPLACES:' + full_package, ' '.join(line[10:].split(', ')))
            elif line.startswith('Conflicts: '):
                d.setVar('RCONFLICTS:' + full_package, ' '.join(line[11:].split(', ')))
            elif line.startswith('Maintainer: '):
                d.setVar('MAINTAINER_' + full_package, line[12:])

    mydir = d.getVar('D', True) + "/../git/"
    for package in d.getVar('PACKAGES', True).split():
        getControlLines(mydir, d, package.split('-')[-1])
}

do_install:append() {
	# remove leftover webinterface garbage
	rm -rf ${D}${libdir}/enigma2/python/Plugins/Extensions/WebInterface
}

python populate_packages:prepend() {
    enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)
}

# Nothing of this recipe should end up in sysroot, so blank it away.
sysroot_stage_all() {
    :
}

do_package_qa() {
}
