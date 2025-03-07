DESCRIPTION = "meta file for enigma2 softcam packages"

require conf/license/openpli-gplv2.inc

PROVIDES = "softcams"

# mipsel only binary softcams (mips32el and mips32el-nf arch)
DEPENDS:append:mipsel = "\
	enigma2-plugin-softcams-cccam \
	enigma2-plugin-softcams-cccam209 \
	enigma2-plugin-softcams-cccam221 \
	enigma2-plugin-softcams-cccam230 \
	enigma2-plugin-softcams-rqcamd \
	enigma2-plugin-softcams-mgcamd135a \
	enigma2-plugin-softcams-mgcamd145c \
	enigma2-plugin-softcams-mgcamd146 \
	"

# armv7a only binary softcams (armv7ahf arch)
DEPENDS:append:armv7a = " \
	enigma2-plugin-softcams-cccam \
	enigma2-plugin-softcams-mgcamd135a \
	"
# armv7ve only binary softcams (cortexa15hf arch)
DEPENDS:append:armv7ve = " \
	enigma2-plugin-softcams-cccam \
	enigma2-plugin-softcams-mgcamd135a \
	"

# softcams with source available
DEPENDS += " \
	enigma2-plugin-softcams-oscam \
	enigma2-plugin-softcams-oscam-emu \
	enigma2-plugin-softcams-oscam-whitelist \
	"
