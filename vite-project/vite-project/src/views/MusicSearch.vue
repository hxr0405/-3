<template>
    <div>
      <div class="header">
        <div class="container mx-auto max-w-4xl text-center flex items-center justify-between">
          <!-- 这个部分保持居中对齐 -->
          <div class="flex-grow text-center">
            <h1 class="text-3xl font-bold mb-2"><i class="fas fa-music mr-2"></i>用音乐治愈心灵</h1>
            <p class="opacity-90">探索治疗性音乐，找到内心的平静</p>
          </div>
          
          <!-- 这个部分用 ml-auto 将疗愈标签推到右侧 -->
          <div class="nav-links ml-auto"> 
            <router-link to="/healing" class="nav-link" id="healing-link">
              <i class="fa-solid fa-heart-pulse"></i>
              疗愈
            </router-link>
          </div>
        </div>
      </div>
      
      <div class="container mx-auto p-4 max-w-4xl mt-8">
        <!-- 搜索框 -->
        <div class="search-box">
          <div class="flex flex-col md:flex-row gap-4">
            <input 
              v-model="searchQuery" 
              type="text" 
              placeholder="输入歌曲名、歌手或专辑名搜索"
              class="search-input flex-grow"
              @keyup.enter="searchMusic"
            >
            <button
              class="bg-blue-600 hover:bg-blue-700 text-white px-6 py-4 rounded-lg font-medium transition-colors"
              @click="searchMusic"
            >
              <i class="fas fa-search mr-2"></i>搜索
            </button>
          </div>
        </div>
  
        <!-- 搜索结果 -->
        <div class="bg-white rounded-xl shadow-sm p-6 mb-8">
          <h2 class="text-xl font-bold mb-6 section-title">搜索结果</h2>
  
          <div class="grid grid-cols-1 gap-4">
            <!-- 单曲列表 -->
            <div v-for="(track, index) in searchResults" :key="'track-' + index" class="music-card">
              <div class="flex flex-col md:flex-row md:items-center justify-between gap-4">
                <div class="flex items-start gap-4">
                  <div class="w-16 h-16 bg-blue-50 rounded-lg flex items-center justify-center text-blue-600">
                    <i :class="track.icon"></i>
                  </div>
                  <div>
                    <h3 class="font-bold text-lg">{{ track.title }}</h3>
                    <p class="text-gray-600">{{ track.artist }} | {{ track.album }}</p>
                    <div class="flex flex-wrap gap-2 mt-2">
                      <span v-for="(badge, badgeIndex) in track.badges" :key="'badge-' + badgeIndex" 
                            :class="['badge', 'badge-' + badge.type]">
                        <i :class="badge.icon"></i>{{ badge.text }}
                      </span>
                    </div>
                  </div>
                </div>
                <button
                  class="bg-blue-100 hover:bg-blue-200 text-blue-800 px-4 py-2 rounded-lg text-sm font-medium transition-colors self-start md:self-center"
                  :disabled="track.disabled"
                  @click="addMusic(track)"
                >
                  <i class="fas fa-plus mr-1" v-if="!track.disabled"></i>{{ track.buttonText || '添加音乐' }}
                </button>
              </div>
            </div>
          </div>
        </div>
  
        <!-- 猜你喜欢 -->
        <div class="bg-white rounded-xl shadow-sm p-6">
          <h2 class="text-xl font-bold mb-6 section-title">猜你喜欢</h2>
  
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <!-- 推荐列表 -->
            <div v-for="(recommendation, index) in recommendations" :key="'rec-' + index" class="music-card">
              <div class="flex items-start gap-4">
                <div class="w-16 h-16 bg-blue-50 rounded-lg flex items-center justify-center text-blue-600">
                  <i :class="recommendation.icon"></i>
                </div>
                <div>
                  <h3 class="font-bold text-lg">{{ recommendation.title }}</h3>
                  <p class="text-gray-600">{{ recommendation.artist }} | {{ recommendation.album }}</p>
                  <div class="flex flex-wrap gap-2 mt-2">
                    <span v-for="(badge, badgeIndex) in recommendation.badges" :key="'badge-' + badgeIndex" 
                          :class="['badge', 'badge-' + badge.type]">
                      <i :class="badge.icon"></i>{{ badge.text }}
                    </span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'MusicSearch',
    data() {
      return {
        searchQuery: '',
        searchResults: [
          {
            title: '舒缓减压音乐',
            artist: '链接松',
            album: '《压力缓解系列》',
            icon: 'fas fa-music text-2xl',
            badges: [
              { type: 'primary', icon: 'fas fa-chart-line mr-1', text: 'No.20 本周推荐' },
              { type: 'secondary', icon: 'fas fa-clock mr-1', text: '45:30' }
            ],
            disabled: false
          },
          {
            title: '助眠海浪声',
            artist: '秒睡眠',
            album: '《睡眠辅助》',
            icon: 'fas fa-water text-2xl',
            badges: [
              { type: 'tertiary', icon: 'fas fa-fire mr-1', text: '今日晚上助眠' },
              { type: 'secondary', icon: 'fas fa-clock mr-1', text: '60:00' }
            ],
            disabled: false
          },
          {
            title: '森林鸟鸣声',
            artist: '林涛听',
            album: '《自然之声》',
            icon: 'fas fa-tree text-2xl',
            badges: [
              { type: 'primary', icon: 'fas fa-calendar mr-1', text: '本周末上线' },
              { type: 'tertiary', icon: 'fas fa-moon mr-1', text: '夜幕播放' }
            ],
            disabled: true,
            buttonText: '即将上线'
          }
        ],
        recommendations: [
          {
            title: '瑜伽冥想音乐',
            artist: '伽宁静',
            album: '《瑜伽治疗系列》',
            icon: 'fas fa-spa text-2xl',
            badges: [
              { type: 'tertiary', icon: 'fas fa-fire mr-1', text: '今日热播' },
              { type: 'primary', icon: 'fas fa-moon mr-1', text: '夜幕推荐' }
            ]
          },
          {
            title: '轻柔钢琴旋律',
            artist: '琴瑟扬',
            album: '《钢琴治愈系列》',
            icon: 'fas fa-piano-keyboard text-2xl',
            badges: [
              { type: 'primary', icon: 'fas fa-chart-line mr-1', text: 'No.10 本周热播' },
              { type: 'secondary', icon: 'fas fa-clock mr-1', text: '32:15' }
            ]
          }
        ]
      }
    },
    methods: {
      searchMusic() {
        // 搜索功能实现
        console.log('搜索: ', this.searchQuery);
        // 这里可以调用API或更新搜索结果
      },
      addMusic(track) {
        // 添加音乐到播放列表
        console.log('添加音乐: ', track.title);
        // 这里可以实现添加到播放列表的逻辑
      }
    }
  }
  </script>
  
  <style scoped>
  .header {
    background-color: #f8f9fa;
    padding: 1.5rem 0;
    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
    margin-bottom: 2rem;
  }
  
  .search-box {
    background-color: rgba(240, 249, 255, 0.7);
    padding: 2rem;
    border-radius: 0.75rem;
    margin-bottom: 2rem;
    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.05), 0 2px 4px -1px rgba(0, 0, 0, 0.03);
  }
  
  .search-input {
    border: 1px solid rgba(0, 0, 0, 0.1);
    border-radius: 0.5rem;
    padding: 1rem 1.5rem;
    font-size: 1rem;
    background-color: white;
    transition: box-shadow 0.2s, border-color 0.2s;
    outline: none;
  }
  
  .search-input:focus {
    border-color: rgba(66, 153, 225, 0.5);
    box-shadow: 0 0 0 3px rgba(66, 153, 225, 0.25);
  }
  
  .music-card {
    border-radius: 1rem;
    padding: 1.25rem;
    background-color: #f8fafc;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
    transition: transform 0.2s, box-shadow 0.2s;
    margin-bottom: 1rem;
  }
  
  .music-card:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  }
  
  .badge {
    display: inline-flex;
    align-items: center;
    padding: 0.25rem 0.75rem;
    border-radius: 9999px;
    font-size: 0.75rem;
    font-weight: 500;
  }
  
  .badge-primary {
    background-color: #e1f5fe;
    color: #2196f3;
  }
  
  .badge-secondary {
    background-color: #f3e5f5;
    color: #9c27b0;
  }
  
  .badge-tertiary {
    background-color: #90caf9;
    color: #42a3c6;
  }
  
  .section-title {
    position: relative;
    padding-left: 1rem;
    font-size: 1.25rem;
    font-weight: bold;
    margin-bottom: 1.5rem;
  }
  
  .section-title:before {
    content: '';
    position: absolute;
    left: 0;
    top: 0;
    bottom: 0;
    width: 4px;
    background: linear-gradient(to bottom, #2b99f3, #1976d2);
    border-radius: 2px;
  }
  
  button i {
    color: rgba(66, 188, 245, 0.6);
  }
  
  button:hover i {
    color: rgba(66, 165, 245, 0.8);
  }
  
  button .fa-search {
    color: rgba(255, 255, 255, 0.6);
  }
  
  button:hover .fa-search {
    color: rgba(255, 255, 255, 0.8);
  }
  
  .nav-link {
    display: inline-flex;
    align-items: center;
    padding: 0.5rem 1rem;
    font-weight: 500;
    color: #1976d2;
    border-radius: 0.5rem;
    transition: background-color 0.2s;
  }
  
  .nav-link:hover {
    background-color: rgba(25, 118, 210, 0.1);
  }
  
  .nav-link i {
    margin-right: 0.5rem;
  }
  </style> 