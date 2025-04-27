<template>
    <div class="healing-music-library">
      <!-- 导航栏 -->
      <nav class="py-4 nav-shadow">
        <div class="container mx-auto px-4 flex justify-between items-center">
          <div class="flex items-center">
            <i class="fas fa-music text-2xl" style="color: var(--primary); margin-right: 8px;"></i>
            <span class="text-2xl font-bold" style="color: var(--primary);"><router-link to="/">HEARTCURE</router-link></span>
          </div>
          <div class="flex space-x-3 mx-auto">
            <button class="category-btn active" data-filter="all">全部</button>
            <button class="category-btn" data-filter="goal">治疗目标</button>
            <button class="category-btn" data-filter="type">音乐类型</button>
            <button class="category-btn" data-filter="scene">使用场景</button>
          </div>
          <router-link to="/healing" class="healing-link">
            <i class="fas fa-spa"></i>
            <span>疗愈</span>
          </router-link>
        </div>
      </nav>
  
      <!-- 主内容区域 -->
      <main class="container mx-auto px-4 py-8">
        <!-- 分类筛选 -->
        <div class="filter-section">
          <!-- 按治疗目标 -->
          <div class="filter-category">
            <h2 class="text-lg font-bold mb-4 flex items-center">
              <i class="fas fa-bullseye mr-2" style="color: var(--primary);"></i> 按治疗目标
            </h2>
            <div class="flex flex-wrap gap-4 justify-center">
              <div class="bubble" style="width: 100px; height: 100px; background-image: url('https://picsum.photos/200/200?random=1');" @click="goToPlaylistType('减压')">
                <span class="bubble-text">减压</span>
              </div>
              <div class="bubble" style="width: 120px; height: 120px; background-image: url('https://picsum.photos/200/200?random=2');" @click="goToPlaylistType('失眠缓解')">
                <span class="bubble-text">失眠缓解</span>
              </div>
              <div class="bubble" style="width: 80px; height: 80px; background-image: url('https://picsum.photos/200/200?random=3');" @click="goToPlaylistType('焦虑改善')">
                <span class="bubble-text">焦虑改善</span>
              </div>
              <div class="bubble" style="width: 110px; height: 110px; background-image: url('https://picsum.photos/200/200?random=4');" @click="goToPlaylistType('专注力提升')">
                <span class="bubble-text">专注力提升</span>
              </div>
            </div>
          </div>
          <!-- 按音乐类型 -->
          <div class="filter-category">
            <h2 class="text-lg font-bold mb-4 flex items-center">
              <i class="fas fa-headphones mr-2" style="color: var(--primary);"></i> 按音乐类型
            </h2>
            <div class="flex flex-wrap gap-4 justify-center">
              <div class="bubble" style="width: 90px; height: 90px; background-image: url('https://picsum.photos/200/200?random=5');" @click="goToPlaylistType('自然白噪音')">
                <span class="bubble-text">自然白噪音</span>
              </div>
              <div class="bubble" style="width: 130px; height: 130px; background-image: url('https://picsum.photos/200/200?random=6');" @click="goToPlaylistType('古典纯音乐')">
                <span class="bubble-text">古典纯音乐</span>
              </div>
              <div class="bubble" style="width: 100px; height: 100px; background-image: url('https://picsum.photos/200/200?random=7');" @click="goToPlaylistType('轻快流行音乐')">
                <span class="bubble-text">轻快流行音乐</span>
              </div>
            </div>
          </div>
          <!-- 按场景 -->
          <div class="filter-category">
            <h2 class="text-lg font-bold mb-4 flex items-center">
              <i class="fas fa-clock mr-2" style="color: var(--primary);"></i> 按场景
            </h2>
            <div class="flex flex-wrap gap-4 justify-center">
              <div class="bubble" style="width: 110px; height: 110px; background-image: url('https://picsum.photos/200/200?random=8');" @click="goToPlaylistType('睡前')">
                <span class="bubble-text">睡前</span>
              </div>
              <div class="bubble" style="width: 90px; height: 90px; background-image: url('https://picsum.photos/200/200?random=9');" @click="goToPlaylistType('工作学习')">
                <span class="bubble-text">工作学习</span>
              </div>
              <div class="bubble" style="width: 120px; height: 120px; background-image: url('https://picsum.photos/200/200?random=10');" @click="goToPlaylistType('运动康复')">
                <span class="bubble-text">运动康复</span>
              </div>
            </div>
          </div>
        </div>
  
        <!-- 资源列表 -->
        <div id="resources" class="grid grid-cols-1 md:grid-cols-3 gap-6">
          <!-- 示例资源卡片 -->
          <div class="resource-card" data-goal="减压" data-type="自然白噪音" data-scene="睡前">
            <img class="resource-img" src="https://picsum.photos/300/200?random=11" alt="资源图片">
            <div class="p-4">
              <span class="tag tag-goal">减压</span>
              <span class="tag tag-type">自然白噪音</span>
              <span class="tag tag-scene">睡前</span>
              <h3 class="text-lg font-bold mb-2">自然白噪音 - 海浪声</h3>
              <p class="text-sm text-gray-600">舒缓的海浪声，帮助您放松身心，减轻压力。</p>
              <button class="play-btn" :data-audio="'https://example.com/sea_waves.mp3'" @click.stop="playAudio($event)">
                <i class="fas fa-play"></i>
              </button>
            </div>
          </div>
          <div class="resource-card" data-goal="专注力提升" data-type="古典纯音乐" data-scene="工作学习">
            <img class="resource-img" src="https://picsum.photos/300/200?random=12" alt="资源图片">
            <div class="p-4">
              <span class="tag tag-goal">专注力提升</span>
              <span class="tag tag-type">古典纯音乐</span>
              <span class="tag tag-scene">工作学习</span>
              <h3 class="text-lg font-bold mb-2">古典纯音乐 - 月光奏鸣曲</h3>
              <p class="text-sm text-gray-600">贝多芬的经典之作，有助于提高专注力。</p>
              <button class="play-btn" :data-audio="'https://example.com/moonlight_sonata.mp3'" @click.stop="playAudio($event)">
                <i class="fas fa-play"></i>
              </button>
            </div>
          </div>
          <div class="resource-card" data-goal="心情愉悦" data-type="轻快流行音乐" data-scene="运动康复">
            <img class="resource-img" src="https://picsum.photos/300/200?random=13" alt="资源图片">
            <div class="p-4">
              <span class="tag tag-goal">心情愉悦</span>
              <span class="tag tag-type">轻快流行音乐</span>
              <span class="tag tag-scene">运动康复</span>
              <h3 class="text-lg font-bold mb-2">轻快流行音乐 - 暖暖</h3>
              <p class="text-sm text-gray-600">温暖的旋律，让您心情愉悦。</p>
              <button class="play-btn" :data-audio="'https://example.com/warm_song.mp3'" @click.stop="playAudio($event)">
                <i class="fas fa-play"></i>
              </button>
            </div>
          </div>
        </div>
      </main>
  
      <!-- 页脚 -->
      <footer class="bg-white shadow-md mt-8">
        <div class="container mx-auto px-4 py-3 text-center text-gray-600">
          &copy; 2025 心灵音疗. 保留所有权利.
        </div>
      </footer>
  
      <!-- 弹出窗口 -->
      <div id="popup" class="popup">
        <h2 class="text-xl font-bold mb-4">资源详情</h2>
        <p id="popup-content" class="text-gray-600"></p>
        <button id="close-popup" class="mt-4 bg-primary text-white px-4 py-2 rounded-md hover:bg-secondary">关闭</button>
      </div>
      <div id="overlay" class="overlay"></div>
  
      <!-- 播放器 -->
      <div class="player" @click="goToPlayPage">
        <div class="track-info">
          <img class="track-cover" src="https://picsum.photos/40/40" alt="曲目封面">
          <div>
            <p class="track-title">暂无播放曲目</p>
            <p class="track-artist"></p>
          </div>
        </div>
        <div class="flex items-center">
          <i class="fas fa-play text-2xl mr-4" id="play-pause-btn"></i>
          <div class="progress-container">
            <div class="progress-bar" id="progress-bar"></div>
          </div>
          <span class="time" id="current-time">0:00</span>
          <span class="time" id="duration">0:00</span>
        </div>
        <input type="range" min="0" max="1" step="0.1" value="0.5" class="volume-slider" id="volume-slider">
        <i class="fas fa-heart favorite-btn" id="favorite-btn"></i>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'HealingMusicLibrary',
    data() {
      return {
        audio: null,
        isPlaying: false,
        isFavorited: false
      }
    },
    mounted() {
      const categoryButtons = document.querySelectorAll('.category-btn');
      const resourceCards = document.querySelectorAll('.resource-card');
      const popup = document.getElementById('popup');
      const overlay = document.getElementById('overlay');
      const closePopup = document.getElementById('close-popup');
      const popupContent = document.getElementById('popup-content');
      const playPauseBtn = document.getElementById('play-pause-btn');
      const volumeSlider = document.getElementById('volume-slider');
      const favoriteBtn = document.getElementById('favorite-btn');
      const progressBar = document.getElementById('progress-bar');
      const currentTime = document.getElementById('current-time');
      const duration = document.getElementById('duration');
      const trackTitle = document.querySelector('.track-title');
      const trackCover = document.querySelector('.track-cover');
  
      this.audio = new Audio();
  
      // 筛选功能
      categoryButtons.forEach(button => {
        button.addEventListener('click', () => {
          categoryButtons.forEach(btn => btn.classList.remove('active'));
          button.classList.add('active');
          const filter = button.dataset.filter;
          if (filter === 'all') {
            resourceCards.forEach(card => card.style.display = 'block');
          } else {
            const filterValue = prompt(`请输入 ${filter === 'goal' ? '治疗目标' : filter === 'type' ? '音乐类型' : '使用场景'}:`);
            if (filterValue) {
              resourceCards.forEach(card => {
                if (card.dataset[filter] === filterValue) {
                  card.style.display = 'block';
                } else {
                  card.style.display = 'none';
                }
              });
            }
          }
        });
      });
  
      // 资源卡片点击弹出详情
      resourceCards.forEach(card => {
        card.addEventListener('click', () => {
          const title = card.querySelector('h3').textContent;
          const description = card.querySelector('p').textContent;
          popupContent.textContent = `${title}\n${description}`;
          popup.style.display = 'block';
          overlay.style.display = 'block';
        });
      });
  
      closePopup.addEventListener('click', () => {
        popup.style.display = 'none';
        overlay.style.display = 'none';
      });
  
      overlay.addEventListener('click', () => {
        popup.style.display = 'none';
        overlay.style.display = 'none';
      });
  
      // 播放/暂停控制
      playPauseBtn.addEventListener('click', () => {
        if (this.isPlaying) {
          this.audio.pause();
          playPauseBtn.classList.remove('fa-pause');
          playPauseBtn.classList.add('fa-play');
        } else {
          if (this.audio.src) {
            this.audio.play();
            playPauseBtn.classList.remove('fa-play');
            playPauseBtn.classList.add('fa-pause');
          }
        }
        this.isPlaying = !this.isPlaying;
      });
  
      // 音量控制
      volumeSlider.addEventListener('input', () => {
        this.audio.volume = volumeSlider.value;
      });
  
      // 收藏功能
      favoriteBtn.addEventListener('click', () => {
        this.isFavorited = !this.isFavorited;
        if (this.isFavorited) {
          favoriteBtn.classList.add('favorited');
        } else {
          favoriteBtn.classList.remove('favorited');
        }
      });
  
      // 进度条更新
      this.audio.addEventListener('timeupdate', () => {
        const progress = (this.audio.currentTime / this.audio.duration) * 100;
        progressBar.style.width = `${progress}%`;
        currentTime.textContent = this.formatTime(this.audio.currentTime);
        if (this.audio.duration) {
          duration.textContent = this.formatTime(this.audio.duration);
        }
      });
    },
    methods: {
      // 格式化时间
      formatTime(time) {
        const minutes = Math.floor(time / 60);
        const seconds = Math.floor(time % 60);
        return `${minutes}:${seconds < 10 ? '0' + seconds : seconds}`;
      },
      // 导航到 PlaylistType 页面
      goToPlaylistType(type) {
        this.$router.push({
          name: 'PlaylistType',
          query: { type: type }
        });
      },
      // 导航到 PlayPage 页面
      goToPlayPage() {
        this.$router.push({ name: 'PlayPage' });
      },
      // 播放音频
      playAudio(event) {
        const audioSrc = event.currentTarget.getAttribute('data-audio');
        const card = event.currentTarget.closest('.resource-card');
        const title = card.querySelector('h3').textContent;
        const cover = card.querySelector('.resource-img').src;
        
        this.audio.src = audioSrc;
        document.querySelector('.track-title').textContent = title;
        document.querySelector('.track-cover').src = cover;
        this.audio.play();
        this.isPlaying = true;
        document.getElementById('play-pause-btn').classList.remove('fa-play');
        document.getElementById('play-pause-btn').classList.add('fa-pause');
      }
    }
  }
  </script>
  
  <style scoped>
  .healing-music-library {
    background-color: var(--light);
    font-family: 'Noto Sans SC', sans-serif;
    color: var(--dark);
    min-height: 100vh;
    padding-bottom: 80px;
  }
  
  .gradient-bg {
    background: linear-gradient(135deg, var(--primary) 0%, var(--secondary) 100%);
  }
  
  .nav-shadow {
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  }
  
  .category-btn {
    transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
    background-color: white;
    color: var(--dark);
    border-radius: 12px;
    padding: 0.5rem 1.25rem;
    font-weight: 500;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
    border: 1px solid rgba(0, 0, 0, 0.05);
  }
  
  .category-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
    background-color: var(--primary);
    color: white;
  }
  
  .category-btn.active {
    background-color: var(--primary);
    color: white;
  }
  
  .resource-card {
    transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
    background-color: white;
    border-radius: 16px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
    overflow: hidden;
    position: relative;
  }
  
  .resource-card:hover {
    transform: translateY(-8px);
    box-shadow: 0 12px 24px rgba(0, 0, 0, 0.1);
  }
  
  .resource-img {
    width: 100%;
    height: 180px;
    object-fit: cover;
    transition: transform 0.5s ease;
  }
  
  .resource-card:hover .resource-img {
    transform: scale(1.05);
  }
  
  .filter-container {
    background-color: white;
    border-radius: 16px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
    padding: 1.5rem;
    margin-bottom: 2rem;
  }
  
  .popup {
    display: none;
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: white;
    border-radius: 16px;
    box-shadow: 0 16px 32px rgba(0, 0, 0, 0.15);
    padding: 2rem;
    z-index: 50;
    width: 90%;
    max-width: 500px;
    animation: fadeIn 0.3s ease-out;
  }
  
  @keyframes fadeIn {
    from {
        opacity: 0;
        transform: translate(-50%, -40%);
    }
    to {
        opacity: 1;
        transform: translate(-50%, -50%);
    }
  }
  
  .overlay {
    display: none;
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    backdrop-filter: blur(5px);
    z-index: 40;
  }
  
  .bubble {
    border-radius: 50%;
    position: relative;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    transition: all 0.3s ease;
    background-size: cover;
    background-position: center;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    overflow: hidden;
    border: 3px solid rgba(255, 255, 255, 0.8);
    background-clip: padding-box;
  }
  
  .bubble::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.3);
    border-radius: 50%;
    transition: background 0.3s ease;
  }
  
  .bubble:hover {
    transform: scale(1.1);
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
  }
  
  .bubble:hover::before {
    background: rgba(0, 0, 0, 0.5);
  }
  
  .bubble-text {
    color: white;
    font-weight: bold;
    text-shadow: 0 2px 4px rgba(0, 0, 0, 0.5);
    z-index: 1;
    font-size: 0.9rem;
    text-align: center;
    padding: 0 10px;
  }
  
  .player {
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100%;
    background-color: white;
    box-shadow: 0 -4px 16px rgba(0, 0, 0, 0.1);
    padding: 12px 20px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    z-index: 30;
    transition: all 0.3s ease;
  }
  
  .player:hover {
    box-shadow: 0 -8px 24px rgba(0, 0, 0, 0.15);
  }
  
  .volume-slider {
    width: 100px;
    appearance: none;
    height: 4px;
    border-radius: 2px;
    background: #E5E7EB;
    outline: none;
  }
  
  .volume-slider::-webkit-slider-thumb {
    -webkit-appearance: none;
    appearance: none;
    width: 16px;
    height: 16px;
    border-radius: 50%;
    background: var(--primary);
    cursor: pointer;
    transition: all 0.2s ease;
  }
  
  .volume-slider::-webkit-slider-thumb:hover {
    transform: scale(1.2);
  }
  
  .favorite-btn {
    cursor: pointer;
    color: #E5E7EB;
    transition: all 0.3s ease;
    font-size: 1.2rem;
  }
  
  .favorite-btn:hover {
    transform: scale(1.1);
  }
  
  .favorite-btn.favorited {
    color: var(--accent);
    animation: heartBeat 0.5s;
  }
  
  @keyframes heartBeat {
    0% {
        transform: scale(1);
    }
    25% {
        transform: scale(1.3);
    }
    50% {
        transform: scale(1);
    }
    75% {
        transform: scale(1.3);
    }
    100% {
        transform: scale(1);
    }
  }
  
  .tag {
    display: inline-block;
    padding: 0.25rem 0.75rem;
    border-radius: 9999px;
    font-size: 0.75rem;
    font-weight: 500;
    margin-right: 0.5rem;
    margin-bottom: 0.5rem;
  }
  
  .tag-goal {
    background-color: #e8f5e9;
    color: #2e727d;
  }
  
  .tag-type {
    background-color: #fff3e0;
    color: #f57c00;
  }
  
  .tag-scene {
    background-color: #e3f2fd;
    color: #1976d2;
  }
  
  .play-btn {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 50px;
    height: 50px;
    background-color: rgba(255, 255, 255, 0.9);
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    color: var(--primary);
    opacity: 0;
    transition: all 0.3s ease;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    border: none;
    cursor: pointer;
  }
  
  .resource-card:hover .play-btn {
    opacity: 1;
    transform: translate(-50%, -50%) scale(1.1);
  }
  
  .premium-badge {
    position: absolute;
    top: 12px;
    right: 12px;
    background: linear-gradient(135deg, #FFD700 0%, #FFA500 100%);
    color: #7C2D12;
    padding: 0.25rem 0.75rem;
    border-radius: 9999px;
    font-size: 0.75rem;
    font-weight: bold;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    z-index: 10;
  }
  
  .progress-container {
    width: 100%;
    height: 4px;
    background-color: #E5E7EB;
    border-radius: 2px;
    margin: 0 1rem;
    overflow: hidden;
  }
  
  .progress-bar {
    height: 100%;
    background-color: var(--primary);
    width: 0%;
    transition: width 0.1s linear;
  }
  
  .time {
    font-size: 0.75rem;
    color: #6B7280;
    min-width: 40px;
  }
  
  .track-info {
    display: flex;
    align-items: center;
    min-width: 200px;
  }
  
  .track-cover {
    width: 40px;
    height: 40px;
    border-radius: 8px;
    margin-right: 12px;
    object-fit: cover;
  }
  
  .track-title {
    font-weight: 500;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    max-width: 150px;
  }
  
  .track-artist {
    font-size: 0.75rem;
    color: #6B7280;
  }
  
  .filter-section {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    gap: 2rem;
    margin-bottom: 2rem;
  }
  
  .filter-category {
    flex: 1 1 300px;
    max-width: 300px;
    background-color: white;
    border-radius: 16px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
    padding: 1.5rem;
  }
  
  .healing-link {
    display: inline-flex;
    align-items: center;
    gap: 0.5rem;
    color: var(--dark);
    text-decoration: none;
    transition: all 0.3s ease;
    padding: 0.5rem 1rem;
    border-radius: 0.5rem;
  }
  
  .healing-link i {
    font-size: 1.8rem;
    color: var(--primary);
    transition: transform 0.3s ease;
  }
  
  .healing-link span {
    font-size: 1.2rem;
    font-weight: 500;
  }
  
  .healing-link:hover {
    background-color: rgba(255, 255, 255, 0.5);
    transform: translateY(-2px);
  }
  
  .healing-link:hover i {
    transform: scale(1.2);
  }
  </style> 