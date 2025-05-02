package eu.fiax.domain

import eu.fiax.domain.chapter.interactor.GetAvailableScanlators
import eu.fiax.domain.chapter.interactor.SetReadStatus
import eu.fiax.domain.chapter.interactor.SyncChaptersWithSource
import eu.fiax.domain.download.interactor.DeleteDownload
import eu.fiax.domain.extension.interactor.GetExtensionLanguages
import eu.fiax.domain.extension.interactor.GetExtensionSources
import eu.fiax.domain.extension.interactor.GetExtensionsByType
import eu.fiax.domain.extension.interactor.TrustExtension
import eu.fiax.domain.manga.interactor.GetExcludedScanlators
import eu.fiax.domain.manga.interactor.SetExcludedScanlators
import eu.fiax.domain.manga.interactor.SetMangaViewerFlags
import eu.fiax.domain.manga.interactor.UpdateManga
import eu.fiax.domain.source.interactor.GetEnabledSources
import eu.fiax.domain.source.interactor.GetIncognitoState
import eu.fiax.domain.source.interactor.GetLanguagesWithSources
import eu.fiax.domain.source.interactor.GetSourcesWithFavoriteCount
import eu.fiax.domain.source.interactor.SetMigrateSorting
import eu.fiax.domain.source.interactor.ToggleIncognito
import eu.fiax.domain.source.interactor.ToggleLanguage
import eu.fiax.domain.source.interactor.ToggleSource
import eu.fiax.domain.source.interactor.ToggleSourcePin
import eu.fiax.domain.track.interactor.AddTracks
import eu.fiax.domain.track.interactor.RefreshTracks
import eu.fiax.domain.track.interactor.SyncChapterProgressWithTrack
import eu.fiax.domain.track.interactor.TrackChapter
import eu.fiax.faxyomi.di.InjektModule
import eu.fiax.faxyomi.di.addFactory
import eu.fiax.faxyomi.di.addSingletonFactory
import mihon.data.repository.ExtensionRepoRepositoryImpl
import mihon.domain.chapter.interactor.FilterChaptersForDownload
import mihon.domain.extensionrepo.interactor.CreateExtensionRepo
import mihon.domain.extensionrepo.interactor.DeleteExtensionRepo
import mihon.domain.extensionrepo.interactor.GetExtensionRepo
import mihon.domain.extensionrepo.interactor.GetExtensionRepoCount
import mihon.domain.extensionrepo.interactor.ReplaceExtensionRepo
import mihon.domain.extensionrepo.interactor.UpdateExtensionRepo
import mihon.domain.extensionrepo.repository.ExtensionRepoRepository
import mihon.domain.extensionrepo.service.ExtensionRepoService
import mihon.domain.upcoming.interactor.GetUpcomingManga
import faxyomi.data.category.CategoryRepositoryImpl
import faxyomi.data.chapter.ChapterRepositoryImpl
import faxyomi.data.history.HistoryRepositoryImpl
import faxyomi.data.manga.MangaRepositoryImpl
import faxyomi.data.release.ReleaseServiceImpl
import faxyomi.data.source.SourceRepositoryImpl
import faxyomi.data.source.StubSourceRepositoryImpl
import faxyomi.data.track.TrackRepositoryImpl
import faxyomi.data.updates.UpdatesRepositoryImpl
import faxyomi.domain.category.interactor.CreateCategoryWithName
import faxyomi.domain.category.interactor.DeleteCategory
import faxyomi.domain.category.interactor.GetCategories
import faxyomi.domain.category.interactor.RenameCategory
import faxyomi.domain.category.interactor.ReorderCategory
import faxyomi.domain.category.interactor.ResetCategoryFlags
import faxyomi.domain.category.interactor.SetDisplayMode
import faxyomi.domain.category.interactor.SetMangaCategories
import faxyomi.domain.category.interactor.SetSortModeForCategory
import faxyomi.domain.category.interactor.UpdateCategory
import faxyomi.domain.category.repository.CategoryRepository
import faxyomi.domain.chapter.interactor.GetChapter
import faxyomi.domain.chapter.interactor.GetChapterByUrlAndMangaId
import faxyomi.domain.chapter.interactor.GetChaptersByMangaId
import faxyomi.domain.chapter.interactor.SetMangaDefaultChapterFlags
import faxyomi.domain.chapter.interactor.ShouldUpdateDbChapter
import faxyomi.domain.chapter.interactor.UpdateChapter
import faxyomi.domain.chapter.repository.ChapterRepository
import faxyomi.domain.history.interactor.GetHistory
import faxyomi.domain.history.interactor.GetNextChapters
import faxyomi.domain.history.interactor.GetTotalReadDuration
import faxyomi.domain.history.interactor.RemoveHistory
import faxyomi.domain.history.interactor.UpsertHistory
import faxyomi.domain.history.repository.HistoryRepository
import faxyomi.domain.manga.interactor.FetchInterval
import faxyomi.domain.manga.interactor.GetDuplicateLibraryManga
import faxyomi.domain.manga.interactor.GetFavorites
import faxyomi.domain.manga.interactor.GetLibraryManga
import faxyomi.domain.manga.interactor.GetManga
import faxyomi.domain.manga.interactor.GetMangaByUrlAndSourceId
import faxyomi.domain.manga.interactor.GetMangaWithChapters
import faxyomi.domain.manga.interactor.NetworkToLocalManga
import faxyomi.domain.manga.interactor.ResetViewerFlags
import faxyomi.domain.manga.interactor.SetMangaChapterFlags
import faxyomi.domain.manga.repository.MangaRepository
import faxyomi.domain.release.interactor.GetApplicationRelease
import faxyomi.domain.release.service.ReleaseService
import faxyomi.domain.source.interactor.GetRemoteManga
import faxyomi.domain.source.interactor.GetSourcesWithNonLibraryManga
import faxyomi.domain.source.repository.SourceRepository
import faxyomi.domain.source.repository.StubSourceRepository
import faxyomi.domain.track.interactor.DeleteTrack
import faxyomi.domain.track.interactor.GetTracks
import faxyomi.domain.track.interactor.GetTracksPerManga
import faxyomi.domain.track.interactor.InsertTrack
import faxyomi.domain.track.repository.TrackRepository
import faxyomi.domain.updates.interactor.GetUpdates
import faxyomi.domain.updates.repository.UpdatesRepository
import uy.kohesive.injekt.api.InjektRegistrar

class DomainModule : InjektModule {

    override fun InjektRegistrar.registerInjectables() {
        addSingletonFactory<CategoryRepository> { CategoryRepositoryImpl(get()) }
        addFactory { GetCategories(get()) }
        addFactory { ResetCategoryFlags(get(), get()) }
        addFactory { SetDisplayMode(get()) }
        addFactory { SetSortModeForCategory(get(), get()) }
        addFactory { CreateCategoryWithName(get(), get()) }
        addFactory { RenameCategory(get()) }
        addFactory { ReorderCategory(get()) }
        addFactory { UpdateCategory(get()) }
        addFactory { DeleteCategory(get(), get(), get()) }

        addSingletonFactory<MangaRepository> { MangaRepositoryImpl(get()) }
        addFactory { GetDuplicateLibraryManga(get()) }
        addFactory { GetFavorites(get()) }
        addFactory { GetLibraryManga(get()) }
        addFactory { GetMangaWithChapters(get(), get()) }
        addFactory { GetMangaByUrlAndSourceId(get()) }
        addFactory { GetManga(get()) }
        addFactory { GetNextChapters(get(), get(), get(), get()) }
        addFactory { GetUpcomingManga(get()) }
        addFactory { ResetViewerFlags(get()) }
        addFactory { SetMangaChapterFlags(get()) }
        addFactory { FetchInterval(get()) }
        addFactory { SetMangaDefaultChapterFlags(get(), get(), get()) }
        addFactory { SetMangaViewerFlags(get()) }
        addFactory { NetworkToLocalManga(get()) }
        addFactory { UpdateManga(get(), get()) }
        addFactory { SetMangaCategories(get()) }
        addFactory { GetExcludedScanlators(get()) }
        addFactory { SetExcludedScanlators(get()) }

        addSingletonFactory<ReleaseService> { ReleaseServiceImpl(get(), get()) }
        addFactory { GetApplicationRelease(get(), get()) }

        addSingletonFactory<TrackRepository> { TrackRepositoryImpl(get()) }
        addFactory { TrackChapter(get(), get(), get(), get()) }
        addFactory { AddTracks(get(), get(), get(), get()) }
        addFactory { RefreshTracks(get(), get(), get(), get()) }
        addFactory { DeleteTrack(get()) }
        addFactory { GetTracksPerManga(get(), get()) }
        addFactory { GetTracks(get()) }
        addFactory { InsertTrack(get()) }
        addFactory { SyncChapterProgressWithTrack(get(), get(), get()) }

        addSingletonFactory<ChapterRepository> { ChapterRepositoryImpl(get()) }
        addFactory { GetChapter(get()) }
        addFactory { GetChaptersByMangaId(get()) }
        addFactory { GetChapterByUrlAndMangaId(get()) }
        addFactory { UpdateChapter(get()) }
        addFactory { SetReadStatus(get(), get(), get(), get(), get()) }
        addFactory { ShouldUpdateDbChapter() }
        addFactory { SyncChaptersWithSource(get(), get(), get(), get(), get(), get(), get(), get(), get()) }
        addFactory { GetAvailableScanlators(get()) }
        addFactory { FilterChaptersForDownload(get(), get(), get(), get()) }

        addSingletonFactory<HistoryRepository> { HistoryRepositoryImpl(get()) }
        addFactory { GetHistory(get()) }
        addFactory { UpsertHistory(get()) }
        addFactory { RemoveHistory(get()) }
        addFactory { GetTotalReadDuration(get()) }

        addFactory { DeleteDownload(get(), get()) }

        addFactory { GetExtensionsByType(get(), get()) }
        addFactory { GetExtensionSources(get()) }
        addFactory { GetExtensionLanguages(get(), get()) }

        addSingletonFactory<UpdatesRepository> { UpdatesRepositoryImpl(get()) }
        addFactory { GetUpdates(get()) }

        addSingletonFactory<SourceRepository> { SourceRepositoryImpl(get(), get()) }
        addSingletonFactory<StubSourceRepository> { StubSourceRepositoryImpl(get()) }
        addFactory { GetEnabledSources(get(), get()) }
        addFactory { GetLanguagesWithSources(get(), get()) }
        addFactory { GetRemoteManga(get()) }
        addFactory { GetSourcesWithFavoriteCount(get(), get()) }
        addFactory { GetSourcesWithNonLibraryManga(get()) }
        addFactory { SetMigrateSorting(get()) }
        addFactory { ToggleLanguage(get()) }
        addFactory { ToggleSource(get()) }
        addFactory { ToggleSourcePin(get()) }
        addFactory { TrustExtension(get(), get()) }

        addSingletonFactory<ExtensionRepoRepository> { ExtensionRepoRepositoryImpl(get()) }
        addFactory { ExtensionRepoService(get(), get()) }
        addFactory { GetExtensionRepo(get()) }
        addFactory { GetExtensionRepoCount(get()) }
        addFactory { CreateExtensionRepo(get(), get()) }
        addFactory { DeleteExtensionRepo(get()) }
        addFactory { ReplaceExtensionRepo(get()) }
        addFactory { UpdateExtensionRepo(get(), get()) }
        addFactory { ToggleIncognito(get()) }
        addFactory { GetIncognitoState(get(), get(), get()) }
    }
}
