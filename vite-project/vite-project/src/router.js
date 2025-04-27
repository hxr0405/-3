import { createRouter, createWebHistory } from "vue-router";

const routes = [
  {
    path: '/',
    name: 'HomePage',
    component: () => import('./views/HomePage.vue')
  },
  {
    path: '/healing',
    name: 'Healing',
    component: () => import('./views/Healing.vue')
  },
  {
    path: '/music-search',
    name: 'MusicSearch',
    component: () => import('./views/MusicSearch.vue')
  },
  {
    path: '/healing-music-library',
    name: 'HealingMusicLibrary',
    component: () => import('./views/HealingMusicLibrary.vue')
  },
  {
    path: '/ai-healing',
    name: 'AIHealing',
    component: () => import('./views/AIHealing.vue')
  },
  {
    path: '/my-groups',
    name: 'MyGroups',
    component: () => import('./views/MyGroups.vue')
  },
  {
    path: '/recommended',
    name: 'Recommended',
    component: () => import('./views/Recommended.vue')
  },
  {
    path: '/write-post',
    name: 'WritePost',
    component: () => import('./views/WritePost.vue')
  },
  {
    path: '/search-posts',
    name: 'SearchPosts',
    component: () => import('./views/SearchPosts.vue')
  },
  {
    path: '/personal-page',
    name: 'PersonalPage',
    component: () => import('./views/PersonalPage.vue')
  },
  {
    path: '/mood-calendar',
    name: 'MoodCalendar',
    component: () => import('./views/MoodCalendar.vue')
  },
  {
    path: '/psychological-diary',
    name: 'PsychologicalDiary',
    component: () => import('./views/PsychologicalDiary.vue')
  },
  {
    path: '/psych-test',
    name: 'PsychTest',
    component: () => import('./views/PsychTest.vue')
  },
  {
    path: '/my-collections',
    name: 'MyCollections',
    component: () => import('./views/MyCollections.vue')
  },
  {
    path: '/my-playlists',
    name: 'MyPlaylists',
    component: () => import('./views/MyPlaylists.vue')
  },
  {
    path: '/my-favorites',
    name: 'MyFavorites',
    component: () => import('./views/MyFavorites.vue')
  },
  {
    path: '/my-likes',
    name: 'MyLikes',
    component: () => import('./views/MyLikes.vue')
  },
  {
    path: '/play-page',
    name: 'PlayPage',
    component: () => import('./views/PlayPage.vue')
  },
  {
    path: '/playlist-type',
    name: 'PlaylistType',
    component: () => import('./views/PlaylistType.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('./views/Login.vue')
  },
  {
    path: '/settings',
    name: 'Settings',
    component: () => import('./views/Settings.vue')
  },
  {
    path: '/message-center',
    name: 'MessageCenter',
    component: () => import('./views/MessageCenter.vue')
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes: routes
});

export default router;