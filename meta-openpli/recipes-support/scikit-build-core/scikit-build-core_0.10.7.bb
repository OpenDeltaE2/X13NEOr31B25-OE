DESCRIPTION = "Scikit-build-core is a build backend for Python that uses CMake to build extension modules"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b4e748e5f102e31c9390dcd6fa66f09"

inherit python_hatchling

DEPENDS = "python3-hatch-vcs-native"

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/scikit-build/scikit-build-core.git;branch=main;protocol=https \
           file://add-more-meta-data-to-dictionary.patch \
"

S = "${WORKDIR}/git"

FILES:${PN} += "*"

BBCLASSEXTEND = "native"
