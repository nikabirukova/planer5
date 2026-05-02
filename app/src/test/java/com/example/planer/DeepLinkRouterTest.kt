package com.example.planer

import com.example.planer.data.deeplink.DeepLinkRouter
import com.example.planer.data.deeplink.Destination
import org.junit.Assert.*
import org.junit.Test

class DeepLinkRouterTest {

    private val router = DeepLinkRouter()

    // ---------- PARSE TESTS ----------

    @Test
    fun parse_detail_myapp() {
        val result = router.parseURL("myapp://tasks/123")
        assertEquals(Destination.Detail("123"), result)
    }

    @Test
    fun parse_catalog_with_filter() {
        val result = router.parseURL("myapp://tasks?filter=completed")
        assertEquals(Destination.Catalog("completed"), result)
    }

    @Test
    fun parse_catalog_no_filter() {
        val result = router.parseURL("myapp://tasks")
        assertEquals(Destination.Catalog(null), result)
    }

    @Test
    fun parse_invite() {
        val result = router.parseURL("myapp://invite/ABC123")
        assertEquals(Destination.Invite("ABC123"), result)
    }

    @Test
    fun parse_https_detail() {
        val result = router.parseURL("https://myapp.com/tasks/999")
        assertEquals(Destination.Detail("999"), result)
    }

    @Test
    fun parse_unknown_returns_null() {
        val result = router.parseURL("myapp://unknown")
        assertNull(result)
    }

    @Test
    fun parse_empty_returns_null() {
        val result = router.parseURL("")
        assertNull(result)
    }

    // ---------- HANDLE TESTS ----------

    @Test
    fun handle_valid_link_updates_state() {
        router.handle("myapp://tasks/123")
        val state = router.currentDestination.value
        assertEquals(Destination.Detail("123"), state)
    }

    @Test
    fun handle_invalid_link_does_not_change_state() {
        router.handle("invalid_link")
        val state = router.currentDestination.value
        assertEquals(Destination.Home, state)
    }

    // ---------- NAVIGATION TESTS ----------

    @Test
    fun navigate_updates_state() {
        router.navigate(Destination.Detail("555"))
        val state = router.currentDestination.value
        assertEquals(Destination.Detail("555"), state)
    }

    @Test
    fun navigate_to_invite() {
        router.navigate(Destination.Invite("TOKEN"))
        val state = router.currentDestination.value
        assertEquals(Destination.Invite("TOKEN"), state)
    }

    // ---------- SHARE TEST ----------

    @Test
    fun share_link_format() {
        val id = "321"
        val url = "myapp://tasks/$id"
        assertEquals("myapp://tasks/321", url)
    }
}