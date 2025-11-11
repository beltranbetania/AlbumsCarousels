package com.example.albumschallenge.ui.home

import com.example.albumschallenge.data.model.Album
import com.example.albumschallenge.data.model.Photo
import com.example.albumschallenge.data.network.AlbumRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val repository: AlbumRepository = mockk()
    private lateinit var viewModel: HomeViewModel
    private val testDispatcher = StandardTestDispatcher()

    private val fakeAlbums = listOf(
        Album(1, 1, "Album 1"),
        Album(2, 2, "Album 2")
    )

    private val fakePhotos = listOf(
        Photo(1, 1, "Title 1", "thumb1", "url1"),
        Photo(2, 1, "Title 2", "thumb2", "url2")
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        coEvery { repository.getAlbums(any(), any()) } returns fakeAlbums
        coEvery { repository.getPhotosByAlbumPaginated(any(), any(), any()) } returns fakePhotos
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial load updates state with data`() = runTest {
        viewModel = HomeViewModel(repository)

        advanceUntilIdle()

        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertEquals(2, state.data?.size)
        assertEquals("Album 1", state.data?.first()?.album?.title)
    }

    @Test
    fun `loadNextPage loads first page data`() = runTest {
        val firstPage = listOf(
            Album(1, 1, "Album 1"),
            Album(2, 2, "Album 2")
        )

        coEvery { repository.getAlbums(any(), any()) } returns firstPage
        coEvery { repository.getPhotosByAlbumPaginated(any(), any(), any()) } returns fakePhotos

        viewModel = HomeViewModel(repository)

        advanceUntilIdle()

        val stateAfterFirstLoad = viewModel.state.value
        assertFalse(stateAfterFirstLoad.isLoading)
        assertEquals(2, stateAfterFirstLoad.data?.size)
        assertEquals("Album 1", stateAfterFirstLoad.data?.first()?.album?.title)

        viewModel.loadNextPage()
        advanceUntilIdle()

        val stateAfterLoadMore = viewModel.state.value
        assertFalse(stateAfterLoadMore.isLoadingMore)
        assertEquals(2, stateAfterLoadMore.data?.size)
    }

    @Test
    fun `repository throws exception updates error state`() = runTest {
        coEvery { repository.getAlbums(any(), any()) } throws RuntimeException("Network error")

        viewModel = HomeViewModel(repository)

        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals("Network error", state.error)
        assertFalse(state.isLoading)
    }
}
