package com.llimapons.echo.domain.events

import java.time.Instant

interface EchoEvent {
    val eventId: String
    val eventKey: String
    val occurredAt: Instant
    val exchange: String
}