<template>
  <div class="playlist-type-page">
    <header class="header">
      <div class="back-button" @click="goBack">
        <i class="fa-solid fa-chevron-left"></i>
      </div>
      <h1 class="title">{{ playlistName }}</h1>
    </header>

    <main class="main-content">
      <div class="playlist-header">
        <div class="playlist-cover-container">
          <img :src="playlistCover" alt="歌单封面" class="playlist-cover">
        </div>
        <div class="playlist-info">
          <h2 class="playlist-title">{{ playlistName }}</h2>
          <p class="playlist-description">{{ playlistDescription }}</p>
          <div class="playlist-stats">
            <span>{{ songCount }}首歌曲</span>
            <span>|</span>
            <span>播放{{ playCount }}次</span>
          </div>
        </div>
      </div>

      <div class="action-buttons">
        <button class="play-all-btn" @click="playAll">
          <i class="fa-solid fa-play"></i>
          <span>播放全部</span>
        </button>
        <button class="favorite-btn" @click="toggleFavorite">
          <i :class="isFavorite ? 'fa-solid fa-heart' : 'fa-regular fa-heart'"></i>
          <span>{{ isFavorite ? '已收藏' : '收藏' }}</span>
        </button>
        <button class="download-btn" @click="downloadPlaylist">
          <i class="fa-solid fa-download"></i>
          <span>下载</span>
        </button>
      </div>

      <div class="song-list">
        <div 
          v-for="(song, index) in songs" 
          :key="index"
          class="song-item"
          @click="playSong(song, index)"
        >
          <div class="song-index">{{ index + 1 }}</div>
          <div class="song-info">
            <div class="song-title">{{ song.title }}</div>
            <div class="song-artist">{{ song.artist }}</div>
          </div>
          <div class="song-duration">{{ formatDuration(song.duration) }}</div>
          <div class="song-actions">
            <i 
              :class="song.isFavorite ? 'fa-solid fa-heart' : 'fa-regular fa-heart'" 
              class="favorite-icon"
              @click.stop="toggleSongFavorite(index)"
            ></i>
            <i class="fa-solid fa-ellipsis-vertical more-icon"></i>
          </div>
        </div>
      </div>

      <div class="similar-playlists">
        <h3 class="section-title">相似歌单</h3>
        <div class="playlist-grid">
          <div 
            v-for="(playlist, index) in similarPlaylists" 
            :key="index"
            class="playlist-card"
            @click="openPlaylist(playlist.id)"
          >
            <img :src="playlist.cover" alt="歌单封面" class="similar-playlist-cover">
            <div class="similar-playlist-title">{{ playlist.name }}</div>
          </div>
        </div>
      </div>
    </main>

    <!-- 播放条 -->
    <div v-if="currentSong" class="playback-bar" @click="goToPlayPage">
      <div class="playback-info">
        <img :src="currentSong.cover" alt="当前播放歌曲" class="playback-cover">
        <div class="playback-details">
          <div class="playback-title">{{ currentSong.title }}</div>
          <div class="playback-artist">{{ currentSong.artist }}</div>
        </div>
      </div>
      <div class="playback-controls">
        <i 
          :class="isPlaying ? 'fa-solid fa-pause' : 'fa-solid fa-play'"
          @click.stop="togglePlay"
        ></i>
        <i class="fa-solid fa-forward-step" @click.stop="nextSong"></i>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PlaylistType',
  data() {
    return {
      playlistName: '轻松冥想音乐',
      playlistDescription: '精选轻松舒缓的冥想曲目，帮助您放松心情，缓解压力。适合在工作疲惫或需要放松时聆听。',
      playlistCover: 'https://picsum.photos/200/200?random=1',
      songCount: 12,
      playCount: 1532,
      isFavorite: false,
      currentSong: null,
      isPlaying: false,
      audio: null,
      currentIndex: 0,
      songs: [
        {
          id: 1,
          title: '森林雨声',
          artist: '自然之声',
          duration: 360, // 6分钟
          cover: 'https://picsum.photos/60/60?random=1',
          audio: 'https://example.com/song1.mp3',
          isFavorite: true
        },
        {
          id: 2,
          title: '冥想钢琴曲',
          artist: '轻音乐大师',
          duration: 480, // 8分钟
          cover: 'https://picsum.photos/60/60?random=2',
          audio: 'https://example.com/song2.mp3',
          isFavorite: false
        },
        {
          id: 3,
          title: '海浪声',
          artist: '自然之声',
          duration: 600, // 10分钟
          cover: 'https://picsum.photos/60/60?random=3',
          audio: 'https://example.com/song3.mp3',
          isFavorite: false
        },
        {
          id: 4,
          title: '禅意冥想',
          artist: '冥想大师',
          duration: 720, // 12分钟
          cover: 'https://picsum.photos/60/60?random=4',
          audio: 'https://example.com/song4.mp3',
          isFavorite: true
        },
        {
          id: 5,
          title: '轻松竖琴',
          artist: '古典乐团',
          duration: 420, // 7分钟
          cover: 'https://picsum.photos/60/60?random=5',
          audio: 'https://example.com/song5.mp3',
          isFavorite: false
        },
        {
          id: 6,
          title: '山泉流水',
          artist: '自然之声',
          duration: 540, // 9分钟
          cover: 'https://picsum.photos/60/60?random=6',
          audio: 'https://example.com/song6.mp3',
          isFavorite: false
        },
        {
          id: 7,
          title: '舒缓萨克斯',
          artist: '爵士乐团',
          duration: 390, // 6分30秒
          cover: 'https://picsum.photos/60/60?random=7',
          audio: 'https://example.com/song7.mp3',
          isFavorite: false
        },
        {
          id: 8,
          title: '静谧夜晚',
          artist: '轻音乐大师',
          duration: 510, // 8分30秒
          cover: 'https://picsum.photos/60/60?random=8',
          audio: 'https://example.com/song8.mp3',
          isFavorite: true
        },
        {
          id: 9,
          title: '禅修音乐',
          artist: '冥想大师',
          duration: 660, // 11分钟
          cover: 'https://picsum.photos/60/60?random=9',
          audio: 'https://example.com/song9.mp3',
          isFavorite: false
        },
        {
          id: 10,
          title: '平静心灵',
          artist: '轻音乐大师',
          duration: 420, // 7分钟
          cover: 'https://picsum.photos/60/60?random=10',
          audio: 'https://example.com/song10.mp3',
          isFavorite: false
        },
        {
          id: 11,
          title: '深度放松',
          artist: '冥想大师',
          duration: 900, // 15分钟
          cover: 'https://picsum.photos/60/60?random=11',
          audio: 'https://example.com/song11.mp3',
          isFavorite: false
        },
        {
          id: 12,
          title: '宁静雨声',
          artist: '自然之声',
          duration: 480, // 8分钟
          cover: 'https://picsum.photos/60/60?random=12',
          audio: 'https://example.com/song12.mp3',
          isFavorite: false
        }
      ],
      similarPlaylists: [
        {
          id: 1,
          name: '深度睡眠音乐',
          cover: 'https://picsum.photos/120/120?random=101'
        },
        {
          id: 2,
          name: '雨声集锦',
          cover: 'https://picsum.photos/120/120?random=102'
        },
        {
          id: 3,
          name: '轻松钢琴曲',
          cover: 'https://picsum.photos/120/120?random=103'
        },
        {
          id: 4,
          name: '清晨冥想',
          cover: 'https://picsum.photos/120/120?random=104'
        },
        {
          id: 5,
          name: '自然之声合集',
          cover: 'https://picsum.photos/120/120?random=105'
        },
        {
          id: 6,
          name: '瑜伽背景音乐',
          cover: 'https://picsum.photos/120/120?random=106'
        }
      ]
    }
  },
  mounted() {
    this.audio = new Audio()
    
    // 监听音频播放结束事件
    this.audio.addEventListener('ended', this.nextSong)
  },
  beforeUnmount() {
    // 移除事件监听
    if (this.audio) {
      this.audio.pause()
      this.audio.removeEventListener('ended', this.nextSong)
    }
  },
  methods: {
    goBack() {
      this.$router.go(-1)
    },
    formatDuration(seconds) {
      const minutes = Math.floor(seconds / 60)
      const remainingSeconds = seconds % 60
      return `${minutes}:${remainingSeconds.toString().padStart(2, '0')}`
    },
    toggleFavorite() {
      this.isFavorite = !this.isFavorite
    },
    toggleSongFavorite(index) {
      this.songs[index].isFavorite = !this.songs[index].isFavorite
    },
    playAll() {
      if (this.songs.length > 0) {
        this.playSong(this.songs[0], 0)
      }
    },
    playSong(song, index) {
      this.currentSong = song
      this.currentIndex = index
      
      if (this.audio) {
        this.audio.src = song.audio
        this.audio.play()
        this.isPlaying = true
      }
    },
    togglePlay() {
      if (!this.audio || !this.currentSong) return
      
      if (this.isPlaying) {
        this.audio.pause()
      } else {
        this.audio.play()
      }
      
      this.isPlaying = !this.isPlaying
    },
    nextSong() {
      if (this.currentIndex < this.songs.length - 1) {
        this.playSong(this.songs[this.currentIndex + 1], this.currentIndex + 1)
      } else {
        // 如果是最后一首，循环到第一首
        this.playSong(this.songs[0], 0)
      }
    },
    goToPlayPage() {
      if (this.currentSong) {
        this.$router.push('/play')
      }
    },
    downloadPlaylist() {
      // 实现下载歌单功能
      alert('开始下载歌单...')
    },
    openPlaylist(playlistId) {
      this.$router.push(`/playlist/${playlistId}`)
    }
  }
}
</script>

<style scoped>
.playlist-type-page {
  background-color: #f5f5f5;
  min-height: 100vh;
  padding-bottom: 60px; /* 为播放条留出空间 */
}

.header {
  display: flex;
  align-items: center;
  padding: 15px;
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  position: sticky;
  top: 0;
  z-index: 10;
}

.back-button {
  font-size: 20px;
  margin-right: 15px;
  cursor: pointer;
}

.title {
  margin: 0;
  font-size: 18px;
  font-weight: bold;
}

.main-content {
  padding: 15px;
}

.playlist-header {
  display: flex;
  margin-bottom: 20px;
  background-color: #fff;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 4px rgba(0,0,0,0.05);
  padding: 15px;
}

.playlist-cover-container {
  flex-shrink: 0;
  margin-right: 15px;
}

.playlist-cover {
  width: 120px;
  height: 120px;
  border-radius: 8px;
  object-fit: cover;
}

.playlist-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.playlist-title {
  margin: 0 0 10px 0;
  font-size: 20px;
  font-weight: bold;
}

.playlist-description {
  margin: 0 0 10px 0;
  font-size: 14px;
  color: #666;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
}

.playlist-stats {
  font-size: 12px;
  color: #999;
}

.playlist-stats span {
  margin-right: 5px;
}

.action-buttons {
  display: flex;
  margin-bottom: 20px;
  gap: 10px;
}

.play-all-btn, .favorite-btn, .download-btn {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 10px;
  border-radius: 4px;
  border: none;
  cursor: pointer;
  font-weight: 500;
  transition: all 0.3s ease;
}

.play-all-btn {
  background-color: #1db954;
  color: white;
}

.favorite-btn, .download-btn {
  background-color: #fff;
  color: #333;
  border: 1px solid #ddd;
}

.play-all-btn:hover {
  background-color: #1ed760;
}

.favorite-btn:hover, .download-btn:hover {
  background-color: #f8f8f8;
}

.action-buttons i {
  margin-right: 5px;
}

.song-list {
  margin-bottom: 20px;
}

.song-item {
  display: flex;
  align-items: center;
  padding: 10px 15px;
  background-color: #fff;
  border-radius: 8px;
  margin-bottom: 10px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.05);
  cursor: pointer;
  transition: transform 0.2s ease;
}

.song-item:hover {
  transform: translateY(-2px);
}

.song-index {
  width: 30px;
  text-align: center;
  font-size: 16px;
  color: #999;
}

.song-info {
  flex: 1;
  overflow: hidden;
  margin: 0 15px;
}

.song-title {
  font-weight: 500;
  margin-bottom: 5px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.song-artist {
  font-size: 12px;
  color: #777;
}

.song-duration {
  font-size: 12px;
  color: #999;
  margin-right: 15px;
}

.song-actions {
  display: flex;
  align-items: center;
}

.favorite-icon {
  color: #ff4757;
  font-size: 18px;
  margin-right: 15px;
}

.more-icon {
  color: #777;
  font-size: 18px;
}

.section-title {
  font-size: 18px;
  font-weight: bold;
  margin: 20px 0 15px;
}

.playlist-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 15px;
}

.playlist-card {
  background-color: #fff;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 4px rgba(0,0,0,0.05);
  transition: transform 0.3s ease;
  cursor: pointer;
}

.playlist-card:hover {
  transform: translateY(-5px);
}

.similar-playlist-cover {
  width: 100%;
  aspect-ratio: 1;
  object-fit: cover;
}

.similar-playlist-title {
  padding: 10px;
  font-size: 14px;
  font-weight: 500;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

/* 播放条样式 */
.playback-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  height: 60px;
  background-color: #fff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 15px;
  box-shadow: 0 -2px 4px rgba(0,0,0,0.1);
  z-index: 100;
}

.playback-info {
  display: flex;
  align-items: center;
  flex: 1;
  overflow: hidden;
}

.playback-cover {
  width: 40px;
  height: 40px;
  border-radius: 4px;
  object-fit: cover;
  margin-right: 10px;
}

.playback-details {
  overflow: hidden;
}

.playback-title {
  font-weight: 500;
  margin-bottom: 3px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.playback-artist {
  font-size: 12px;
  color: #777;
}

.playback-controls {
  display: flex;
  align-items: center;
}

.playback-controls i {
  font-size: 24px;
  color: #1db954;
  margin-left: 20px;
  cursor: pointer;
}
</style> 