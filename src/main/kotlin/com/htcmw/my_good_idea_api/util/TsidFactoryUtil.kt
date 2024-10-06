package com.htcmw.my_good_idea_api.util

import io.hypersistence.tsid.TSID
import java.time.Clock

object TsidUtil {
    private val nodeId: Int = System.getProperty("node.id")?.toIntOrNull() ?: 1  // 환경 변수에서 노드 ID 읽기, 기본값 1
    private val tsidFactory: TSID.Factory = TSID.Factory.builder()
        .withNodeBits(8)
        .withNode(nodeId)
        .withClock(Clock.systemUTC())
        .build()

    // TSID 생성 메서드
    fun generateTsid(): String {
        return tsidFactory.generate().toString()
    }
}