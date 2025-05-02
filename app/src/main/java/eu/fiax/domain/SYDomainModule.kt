package eu.fiax.domain

import android.app.Application
import eu.fiax.domain.manga.interactor.CreateSortTag
import eu.fiax.domain.manga.interactor.DeleteSortTag
import eu.fiax.domain.manga.interactor.GetPagePreviews
import eu.fiax.domain.manga.interactor.GetSortTag
import eu.fiax.domain.manga.interactor.ReorderSortTag
import eu.fiax.domain.source.interactor.CreateSourceCategory
import eu.fiax.domain.source.interactor.DeleteSourceCategory
import eu.fiax.domain.source.interactor.GetExhSavedSearch
import eu.fiax.domain.source.interactor.GetShowLatest
import eu.fiax.domain.source.interactor.GetSourceCategories
import eu.fiax.domain.source.interactor.RenameSourceCategory
import eu.fiax.domain.source.interactor.SetSourceCategories
import eu.fiax.domain.source.interactor.ToggleExcludeFromDataSaver
import eu.fiax.faxyomi.di.InjektModule
import eu.fiax.faxyomi.di.addFactory
import eu.fiax.faxyomi.di.addSingletonFactory
import eu.fiax.faxyomi.source.online.MetadataSource
import exh.search.SearchEngine
import faxyomi.data.manga.CustomMangaRepositoryImpl
import faxyomi.data.manga.FavoritesEntryRepositoryImpl
import faxyomi.data.manga.MangaMergeRepositoryImpl
import faxyomi.data.manga.MangaMetadataRepositoryImpl
import faxyomi.data.source.FeedSavedSearchRepositoryImpl
import faxyomi.data.source.SavedSearchRepositoryImpl
import faxyomi.domain.chapter.interactor.DeleteChapters
import faxyomi.domain.chapter.interactor.GetChapterByUrl
import faxyomi.domain.chapter.interactor.GetMergedChaptersByMangaId
import faxyomi.domain.history.interactor.GetHistoryByMangaId
import faxyomi.domain.manga.interactor.DeleteByMergeId
import faxyomi.domain.manga.interactor.DeleteFavoriteEntries
import faxyomi.domain.manga.interactor.DeleteMangaById
import faxyomi.domain.manga.interactor.DeleteMergeById
import faxyomi.domain.manga.interactor.GetAllManga
import faxyomi.domain.manga.interactor.GetCustomMangaInfo
import faxyomi.domain.manga.interactor.GetExhFavoriteMangaWithMetadata
import faxyomi.domain.manga.interactor.GetFavoriteEntries
import faxyomi.domain.manga.interactor.GetFlatMetadataById
import faxyomi.domain.manga.interactor.GetIdsOfFavoriteMangaWithMetadata
import faxyomi.domain.manga.interactor.GetManga
import faxyomi.domain.manga.interactor.GetMangaBySource
import faxyomi.domain.manga.interactor.GetMergedManga
import faxyomi.domain.manga.interactor.GetMergedMangaById
import faxyomi.domain.manga.interactor.GetMergedMangaForDownloading
import faxyomi.domain.manga.interactor.GetMergedReferencesById
import faxyomi.domain.manga.interactor.GetReadMangaNotInLibraryView
import faxyomi.domain.manga.interactor.GetSearchMetadata
import faxyomi.domain.manga.interactor.GetSearchTags
import faxyomi.domain.manga.interactor.GetSearchTitles
import faxyomi.domain.manga.interactor.InsertFavoriteEntries
import faxyomi.domain.manga.interactor.InsertFavoriteEntryAlternative
import faxyomi.domain.manga.interactor.InsertFlatMetadata
import faxyomi.domain.manga.interactor.InsertMergedReference
import faxyomi.domain.manga.interactor.SetCustomMangaInfo
import faxyomi.domain.manga.interactor.UpdateMergedSettings
import faxyomi.domain.manga.repository.CustomMangaRepository
import faxyomi.domain.manga.repository.FavoritesEntryRepository
import faxyomi.domain.manga.repository.MangaMergeRepository
import faxyomi.domain.manga.repository.MangaMetadataRepository
import faxyomi.domain.source.interactor.CountFeedSavedSearchBySourceId
import faxyomi.domain.source.interactor.CountFeedSavedSearchGlobal
import faxyomi.domain.source.interactor.DeleteFeedSavedSearchById
import faxyomi.domain.source.interactor.DeleteSavedSearchById
import faxyomi.domain.source.interactor.GetFeedSavedSearchBySourceId
import faxyomi.domain.source.interactor.GetFeedSavedSearchGlobal
import faxyomi.domain.source.interactor.GetSavedSearchById
import faxyomi.domain.source.interactor.GetSavedSearchBySourceId
import faxyomi.domain.source.interactor.GetSavedSearchBySourceIdFeed
import faxyomi.domain.source.interactor.GetSavedSearchGlobalFeed
import faxyomi.domain.source.interactor.InsertFeedSavedSearch
import faxyomi.domain.source.interactor.InsertSavedSearch
import faxyomi.domain.source.repository.FeedSavedSearchRepository
import faxyomi.domain.source.repository.SavedSearchRepository
import faxyomi.domain.track.interactor.IsTrackUnfollowed
import uy.kohesive.injekt.api.InjektRegistrar
import xyz.nulldev.ts.api.http.serializer.FilterSerializer

class SYDomainModule : InjektModule {

    override fun InjektRegistrar.registerInjectables() {
        addFactory { GetShowLatest(get()) }
        addFactory { ToggleExcludeFromDataSaver(get()) }
        addFactory { SetSourceCategories(get()) }
        addFactory { GetAllManga(get()) }
        addFactory { GetMangaBySource(get()) }
        addFactory { DeleteChapters(get()) }
        addFactory { DeleteMangaById(get()) }
        addFactory { FilterSerializer() }
        addFactory { GetHistoryByMangaId(get()) }
        addFactory { GetChapterByUrl(get()) }
        addFactory { GetSourceCategories(get()) }
        addFactory { CreateSourceCategory(get()) }
        addFactory { RenameSourceCategory(get(), get()) }
        addFactory { DeleteSourceCategory(get()) }
        addFactory { GetSortTag(get()) }
        addFactory { CreateSortTag(get(), get()) }
        addFactory { DeleteSortTag(get(), get()) }
        addFactory { ReorderSortTag(get(), get()) }
        addFactory { GetPagePreviews(get(), get()) }
        addFactory { SearchEngine() }
        addFactory { IsTrackUnfollowed() }
        addFactory { GetReadMangaNotInLibraryView(get()) }

        // Required for [MetadataSource]
        addFactory<MetadataSource.GetMangaId> { GetManga(get()) }
        addFactory<MetadataSource.GetFlatMetadataById> { GetFlatMetadataById(get()) }
        addFactory<MetadataSource.InsertFlatMetadata> { InsertFlatMetadata(get()) }

        addSingletonFactory<MangaMetadataRepository> { MangaMetadataRepositoryImpl(get()) }
        addFactory { GetFlatMetadataById(get()) }
        addFactory { InsertFlatMetadata(get()) }
        addFactory { GetExhFavoriteMangaWithMetadata(get()) }
        addFactory { GetSearchMetadata(get()) }
        addFactory { GetSearchTags(get()) }
        addFactory { GetSearchTitles(get()) }
        addFactory { GetIdsOfFavoriteMangaWithMetadata(get()) }

        addSingletonFactory<MangaMergeRepository> { MangaMergeRepositoryImpl(get()) }
        addFactory { GetMergedManga(get()) }
        addFactory { GetMergedMangaById(get()) }
        addFactory { GetMergedReferencesById(get()) }
        addFactory { GetMergedChaptersByMangaId(get(), get()) }
        addFactory { InsertMergedReference(get()) }
        addFactory { UpdateMergedSettings(get()) }
        addFactory { DeleteByMergeId(get()) }
        addFactory { DeleteMergeById(get()) }
        addFactory { GetMergedMangaForDownloading(get()) }

        addSingletonFactory<FavoritesEntryRepository> { FavoritesEntryRepositoryImpl(get()) }
        addFactory { GetFavoriteEntries(get()) }
        addFactory { InsertFavoriteEntries(get()) }
        addFactory { DeleteFavoriteEntries(get()) }
        addFactory { InsertFavoriteEntryAlternative(get()) }

        addSingletonFactory<SavedSearchRepository> { SavedSearchRepositoryImpl(get()) }
        addFactory { GetSavedSearchById(get()) }
        addFactory { GetSavedSearchBySourceId(get()) }
        addFactory { DeleteSavedSearchById(get()) }
        addFactory { InsertSavedSearch(get()) }
        addFactory { GetExhSavedSearch(get(), get(), get()) }

        addSingletonFactory<FeedSavedSearchRepository> { FeedSavedSearchRepositoryImpl(get()) }
        addFactory { InsertFeedSavedSearch(get()) }
        addFactory { DeleteFeedSavedSearchById(get()) }
        addFactory { GetFeedSavedSearchGlobal(get()) }
        addFactory { GetFeedSavedSearchBySourceId(get()) }
        addFactory { CountFeedSavedSearchGlobal(get()) }
        addFactory { CountFeedSavedSearchBySourceId(get()) }
        addFactory { GetSavedSearchGlobalFeed(get()) }
        addFactory { GetSavedSearchBySourceIdFeed(get()) }

        addSingletonFactory<CustomMangaRepository> { CustomMangaRepositoryImpl(get<Application>()) }
        addFactory { GetCustomMangaInfo(get()) }
        addFactory { SetCustomMangaInfo(get()) }
    }
}
