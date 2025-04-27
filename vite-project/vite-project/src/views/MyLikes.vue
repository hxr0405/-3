<template>
  <div>
    <header class="header">
      <h1>
        <i class="fas fa-heart header-icon"></i>
        我喜欢的
      </h1>
      <p>查看您喜欢的所有内容</p>
      <button class="user-btn" title="个人中心" @click="goToProfile">
        <i class="fas fa-user"></i>
      </button>
    </header>
    <div class="play-all" @click="playAll">
      <i class="fas fa-play"></i>
      <span>播放全部</span>
      <span class="play-all-number">{{ items.length }}</span>
    </div>
    <div class="sort-filters">
      <button class="filter-tag" @click.stop="toggleLikePanel">
        喜欢程度
        <i class="fas fa-chevron-down"></i>
      </button>
      <button class="filter-tag" @click.stop="toggleTimePanel">
        收藏时间
        <i class="fas fa-chevron-down"></i>
      </button>
      <div class="filter-panel" :class="{ active: likePanelActive }">
        <h3 class="font-medium mb-3">选择喜欢程度</h3>
        <div v-for="option in likeOptions" :key="option.value" class="filter-option" :class="{ selected: likeFilter === option.value }" @click="selectLike(option.value)">
          <span v-html="option.stars"></span>
          {{ option.label }}
        </div>
        <div class="filter-actions">
          <button class="filter-btn filter-reset" @click="resetLike">重置</button>
          <button class="filter-btn filter-apply" @click="applyLike">应用</button>
        </div>
      </div>
      <div class="filter-panel" :class="{ active: timePanelActive }">
        <h3 class="font-medium mb-3">选择收藏时间</h3>
        <div v-for="option in timeOptions" :key="option.value" class="filter-option" :class="{ selected: timeFilter === option.value }" @click="selectTime(option.value)">
          <i class="far fa-clock mr-2"></i>{{ option.label }}
        </div>
        <div class="filter-actions">
          <button class="filter-btn filter-reset" @click="resetTime">重置</button>
          <button class="filter-btn filter-apply" @click="applyTime">应用</button>
        </div>
      </div>
    </div>
    <div class="overlay" :class="{ active: likePanelActive || timePanelActive }" @click="closePanels"></div>
    <div class="collection">
      <div v-for="(item, idx) in filteredItems" :key="idx" class="collection-item" @click="playItem(item)">
        <div class="item-details">
          <div class="item-cover"></div>
          <div class="item-info">
            <h2 class="item-title">{{ item.title }}</h2>
            <p class="item-artist">{{ item.artist }}</p>
          </div>
        </div>
        <div class="item-icons">
          <i class="fas fa-share"></i>
          <i class="fas fa-ellipsis-v"></i>
        </div>
      </div>
    </div>
    <div class="playback-bar" v-show="currentPlaying">
      <div class="playback-details">
        <div class="playback-cover"></div>
        <div class="playback-info">
          <h3 class="playback-title">{{ currentPlaying ? currentPlaying.title : '' }}</h3>
          <p class="playback-artist">{{ currentPlaying ? currentPlaying.artist : '' }}</p>
        </div>
      </div>
      <div class="playback-controls">
        <i class="fas fa-heart"></i>
        <i :class="['fas', isPlaying ? 'fa-pause' : 'fa-play']" @click.stop="togglePlay"></i>
        <i class="fas fa-ellipsis-v"></i>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MyLikes',
  data() {
    return {
      items: [
        { title: '青柠', artist: '徐秉龙、桃十五', like: 5, time: 'today' },
        { title: '画 (Live Piano Session Ⅱ)', artist: 'G.E.M. 邓紫棋', like: 4, time: 'week' },
        // 可继续添加更多收藏项
      ],
      likePanelActive: false,
      timePanelActive: false,
      likeFilter: 5,
      timeFilter: 'today',
      likeOptions: [
        { value: 5, label: '(最爱)', stars: '<i class="fas fa-star text-yellow-400 mr-2"></i>'.repeat(5) },
        { value: 4, label: '(很喜欢)', stars: '<i class="fas fa-star text-yellow-400 mr-2"></i>'.repeat(4) + '<i class="far fa-star text-yellow-400 mr-2"></i>' },
        { value: 3, label: '(喜欢)', stars: '<i class="fas fa-star text-yellow-400 mr-2"></i>'.repeat(3) + '<i class="far fa-star text-yellow-400 mr-2"></i>'.repeat(2) },
        { value: 2, label: '(一般)', stars: '<i class="fas fa-star text-yellow-400 mr-2"></i>'.repeat(2) + '<i class="far fa-star text-yellow-400 mr-2"></i>'.repeat(3) },
        { value: 1, label: '(不太喜欢)', stars: '<i class="fas fa-star text-yellow-400 mr-2"></i>' + '<i class="far fa-star text-yellow-400 mr-2"></i>'.repeat(4) },
      ],
      timeOptions: [
        { value: 'today', label: '今天' },
        { value: 'week', label: '本周' },
        { value: 'month', label: '本月' },
        { value: 'year', label: '今年' },
        { value: 'older', label: '更早' },
      ],
      currentPlaying: null,
      isPlaying: false,
    };
  },
  computed: {
    filteredItems() {
      // 这里只做静态筛选演示
      return this.items.filter(item => item.like === this.likeFilter && item.time === this.timeFilter);
    }
  },
  methods: {
    goToProfile() {
      this.$router.push({ name: 'PersonalPage' });
    },
    toggleLikePanel() {
      this.likePanelActive = !this.likePanelActive;
      this.timePanelActive = false;
    },
    toggleTimePanel() {
      this.timePanelActive = !this.timePanelActive;
      this.likePanelActive = false;
    },
    closePanels() {
      this.likePanelActive = false;
      this.timePanelActive = false;
    },
    selectLike(val) {
      this.likeFilter = val;
    },
    selectTime(val) {
      this.timeFilter = val;
    },
    resetLike() {
      this.likeFilter = 5;
    },
    resetTime() {
      this.timeFilter = 'today';
    },
    applyLike() {
      this.likePanelActive = false;
    },
    applyTime() {
      this.timePanelActive = false;
    },
    playItem(item) {
      this.currentPlaying = item;
      this.isPlaying = true;
    },
    togglePlay() {
      this.isPlaying = !this.isPlaying;
    },
    playAll() {
      alert('开始播放所有收藏歌曲');
    }
  }
}
</script>

<style>
:root {
  --main-bg-color: #e8f8ff;
  --card-bg-color: #ffffff;
  --text-color: #212529;
  --secondary-text-color: #6c757d;
  --button-bg-color: #c7e8ff;
  --hover-button-bg-color: #a8d8ff;
  --accent-color: #d8f0ff;
  --nav-bg-color: rgba(255, 255, 255, 0.95);
  --nav-shadow: 0 2px 12px rgba(184, 222, 245, 0.3);
  --active-filter-color: #84bfd9;
}
body {
  font-family: 'Inter', sans-serif;
  background-color: var(--main-bg-color);
  color: var(--text-color);
  margin: 0;
  padding: 0;
}
.header {
  background: linear-gradient(135deg, #84bfd9 100%);
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
  color: white;
  padding: 2rem;
  text-align: center;
  margin-bottom: 2rem;
  position: relative;
}
.header h1 {
  font-family: 'Inter', sans-serif;
  font-size: 2.25rem;
  font-weight: bold;
  margin: 0;
  display: flex;
  align-items: center;
  justify-content: center;
}
.header-icon {
  margin-right: 0.75rem;
  font-size: 1.8rem;
  color: #ff6b6b;
}
.header p {
  opacity: 0.9;
  margin: 0.5rem 0 0;
  font-size: 1.125rem;
}
.user-btn {
  position: absolute;
  top: 1.5rem;
  right: 1.5rem;
  background-color: rgba(255, 255, 255, 0.2);
  color: white;
  text-align: center;
  padding: 0.75rem;
  border: none;
  border-radius: 50%;
  cursor: pointer;
  transition: all 0.3s ease;
  width: 3rem;
  height: 3rem;
  display: flex;
  align-items: center;
  justify-content: center;
}
.user-btn:hover {
  background-color: rgba(255, 255, 255, 0.3);
  transform: scale(1.05);
}
.user-btn i {
  font-size: 1.5rem;
}
.play-all {
  display: flex;
  align-items: center;
  margin: 1rem;
  cursor: pointer;
  transition: transform 0.2s ease;
}
.play-all:hover {
  transform: translateX(5px);
}
.play-all i {
  font-size: 1.5rem;
  color: var(--button-bg-color);
  margin-right: 0.5rem;
}
.play-all span {
  font-size: 1rem;
}
.play-all-number {
  color: var(--secondary-text-color);
  margin-left: 0.5rem;
}
.sort-filters {
  display: flex;
  align-items: center;
  margin: 1rem;
  position: relative;
}
.filter-tag {
  background-color: var(--card-bg-color);
  color: var(--text-color);
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 0.25rem;
  margin-right: 0.5rem;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  position: relative;
}
.filter-tag:hover {
  background-color: var(--accent-color);
}
.filter-tag i {
  margin-left: 0.5rem;
  font-size: 0.8rem;
}
.filter-panel {
  position: absolute;
  top: 100%;
  left: 0;
  background-color: white;
  border-radius: 0.5rem;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  padding: 1rem;
  z-index: 100;
  display: none;
  width: 300px;
  max-height: 400px;
  overflow-y: auto;
}
.filter-panel.active {
  display: block;
}
.filter-option {
  padding: 0.5rem;
  cursor: pointer;
  border-radius: 0.25rem;
  margin-bottom: 0.25rem;
  transition: all 0.2s ease;
}
.filter-option:hover {
  background-color: var(--accent-color);
}
.filter-option.selected {
  background-color: var(--button-bg-color);
  font-weight: 500;
}
.filter-actions {
  display: flex;
  justify-content: space-between;
  margin-top: 1rem;
  padding-top: 1rem;
  border-top: 1px solid #eee;
}
.filter-btn {
  padding: 0.5rem 1rem;
  border-radius: 0.25rem;
  cursor: pointer;
  transition: all 0.2s ease;
  border: none;
}
.filter-apply {
  background-color: var(--active-filter-color);
  color: white;
}
.filter-reset {
  background-color: transparent;
  color: var(--secondary-text-color);
}
.filter-btn:hover {
  opacity: 0.9;
}
.collection {
  background: none;
}
.collection-item {
  background-color: var(--card-bg-color);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  border-radius: 0.5rem;
  padding: 1rem;
  margin: 1rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  transition: all 0.3s ease;
  cursor: pointer;
}
.collection-item:hover {
  transform: translateY(-3px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}
.item-details {
  display: flex;
  align-items: center;
}
.item-cover {
  width: 48px;
  height: 48px;
  border-radius: 0.25rem;
  margin-right: 1rem;
  background-color: var(--accent-color);
  transition: transform 0.3s ease;
}
.collection-item:hover .item-cover {
  transform: scale(1.05);
}
.item-info {
  display: flex;
  flex-direction: column;
}
.item-title {
  font-size: 1rem;
  font-weight: 500;
  margin: 0;
  transition: color 0.3s ease;
}
.collection-item:hover .item-title {
  color: var(--active-filter-color);
}
.item-artist {
  font-size: 0.875rem;
  color: var(--secondary-text-color);
  margin-top: 0.25rem;
}
.item-icons i {
  font-size: 1.25rem;
  color: var(--secondary-text-color);
  margin-left: 0.5rem;
  cursor: pointer;
  transition: all 0.3s ease;
}
.item-icons i:hover {
  color: var(--button-bg-color);
  transform: scale(1.1);
}
.playback-bar {
  background-color: var(--card-bg-color);
  padding: 0.5rem 1rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.05);
  z-index: 100;
}
.playback-details {
  display: flex;
  align-items: center;
}
.playback-cover {
  width: 36px;
  height: 36px;
  border-radius: 0.25rem;
  background-color: var(--accent-color);
  margin-right: 0.5rem;
}
.playback-info {
  display: flex;
  flex-direction: column;
}
.playback-title {
  font-size: 0.875rem;
  font-weight: 500;
  margin: 0;
}
.playback-artist {
  font-size: 0.75rem;
  color: var(--secondary-text-color);
  margin-top: 0.25rem;
}
.playback-controls {
  display: flex;
  align-items: center;
}
.playback-controls i {
  font-size: 1.25rem;
  color: var(--text-color);
  margin-left: 0.5rem;
  cursor: pointer;
  transition: all 0.3s ease;
}
.playback-controls i:hover {
  color: var(--button-bg-color);
  transform: scale(1.1);
}
.playback-controls .fa-heart {
  color: #ff6b6b;
}
.overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 50;
  display: none;
}
.overlay.active {
  display: block;
}
@media (max-width: 768px) {
  .header h1 {
    font-size: 1.375rem;
  }
  .header-icon {
    font-size: 1.4rem;
    margin-right: 0.5rem;
  }
  .play-all {
    font-size: 0.9rem;
  }
  .collection-item {
    flex-direction: column;
    align-items: flex-start;
    padding: 0.75rem;
  }
  .item-details {
    flex-direction: column;
    align-items: flex-start;
  }
  .item-cover {
    width: 36px;
    height: 36px;
    margin-bottom: 0.5rem;
  }
}
</style> 