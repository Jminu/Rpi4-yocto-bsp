SUMMARY = "Sensor Monitoring App"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = "\
        file://app.c \
        file://sensor-app.service \
        "

S = "${WORKDIR}"

inherit systemd

SYSTEMD_SERVICE:${PN} = "sensor-app.service"

SYSTEMD_AUTO_ENABLE:${PN} = "enable"

RDEPENDS:${PN} = "sensor-drivers"

do_compile() {
    ${CC} ${CFLAGS} ${LDFLAGS} app.c -o sensor-app
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 sensor-app ${D}${bindir}/

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/sensor-app.service ${D}${systemd_system_unitdir}/
}

FILES:${PN} = "\
        ${bindir}/sensor-app \
        ${systemd_system_unitdir}/sensor-app.service \
        "
