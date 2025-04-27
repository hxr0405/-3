<script>
export default {
  name: 'MyPlaylists',
  data() {
    return {
      playlists: [
        { id: 1, name: '抗焦虑深度疗愈', cover: 'https://via.placeholder.com/60x60', songCount: 18, duration: 62, description: '含脑波音乐', tags: ['助眠', '冥想'], time: '更新于 昨天', category: 'healing' },
        { id: 2, name: '放松舒缓精选', cover: 'https://via.placeholder.com/60x60', songCount: 12, duration: 45, description: '轻松旋律', tags: ['放松', '舒缓'], time: '创建于 刚刚', category: 'relax' },
        { id: 3, name: '情绪提升活力', cover: 'https://via.placeholder.com/60x60', songCount: 20, duration: 70, description: '活力满满', tags: ['情绪', '活力'], time: '创建于 刚刚', category: 'mood' }
      ],
      tags: [
        { label: '全部', value: 'all' },
        { label: '治疗专用', value: 'healing' },
        { label: '放松舒缓', value: 'relax' },
        { label: '情绪提升', value: 'mood' }
      ],
      activeTag: 'all',
      showModal: false,
      newPlaylist: { name: '', description: '', category: 'healing' }
    }
  },
  computed: {
    filteredPlaylists() {
      if (this.activeTag === 'all') return this.playlists;
      return this.playlists.filter(p => p.category === this.activeTag);
    },
    totalSongs() {
      return this.playlists.reduce((sum, p) => sum + p.songCount, 0);
    }
  },
  methods: {
    filterPlaylists(tag, e) {
      this.activeTag = tag;
    },
    goToProfile() {
      this.$router.push('/personal-page');
    },
    createPlaylist() {
      if (!this.newPlaylist.name.trim()) {
        alert('歌单名称不能为空！');
        return;
      }
      this.playlists.push({
        id: Date.now(),
        name: this.newPlaylist.name,
        cover: 'https://via.placeholder.com/60x60',
        songCount: 0,
        duration: 0,
        description: this.newPlaylist.description,
        tags: [],
        time: '创建于 刚刚',
        category: this.newPlaylist.category
      });
      this.showModal = false;
      this.newPlaylist = { name: '', description: '', category: 'healing' };
    }
  }
}
</script>

<template>
  <div class="my-playlists font-sans">
    <div class="header">
      <div class="title">我的歌单 ({{ playlists.length }}/{{ totalSongs }}首)</div>
      <div class="header-buttons">
        <button class="create-btn" @click="showModal = true">新建歌单</button>
        <button class="exit-btn" @click="goToProfile">退出</button>
      </div>
    </div>
    <div class="tags">
      <span v-for="tag in tags" :key="tag.value" :class="['tag', { active: activeTag === tag.value }]" @click="filterPlaylists(tag.value, $event)">{{ tag.label }}</span>
    </div>
    <div v-for="playlist in filteredPlaylists" :key="playlist.id" class="playlist-card" :data-category="playlist.category">
      <div class="playlist-header">
        <img :src="playlist.cover" class="playlist-cover" alt="封面">
        <div class="playlist-info">
          <h3>{{ playlist.name }}</h3>
          <div class="playlist-meta">{{ playlist.songCount }}首 · {{ playlist.duration }}分钟 · {{ playlist.description }}</div>
          <div class="playlist-tags">
            <span v-for="(tag, tIdx) in playlist.tags" :key="tIdx" class="playlist-tag">{{ tag }}</span>
          </div>
          <div class="playlist-time">{{ playlist.time }}</div>
        </div>
      </div>
    </div>
    <!-- 新建歌单弹窗 -->
    <div class="modal" v-show="showModal">
      <div class="modal-content">
        <h2>新建歌单</h2>
        <input v-model="newPlaylist.name" placeholder="歌单名称">
        <textarea v-model="newPlaylist.description" rows="4" placeholder="歌单描述"></textarea>
        <select v-model="newPlaylist.category">
          <option value="healing">治疗专用</option>
          <option value="relax">放松舒缓</option>
          <option value="mood">情绪提升</option>
        </select>
        <button @click="createPlaylist">创建</button>
        <button @click="showModal = false">取消</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}
body {
  background-color: #f7f9fc;
  padding: 30px;
}
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  padding: 18px 24px;
  background-color: #ffffff;
  border-radius: 12px;
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
}
.title {
  font-size: 26px;
  font-weight: bold;
  color: #333;
}
.header-buttons {
  display: flex;
  gap: 12px;
}
.create-btn,
.exit-btn {
  background-color: #4a90e2;
  color: white;
  border: none;
  padding: 12px 20px;
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
  font-size: 14px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
}
.create-btn:hover,
.exit-btn:hover {
  background-color: #357ab7;
  transform: translateY(-2px);
}
.tags {
  display: flex;
  gap: 12px;
  margin-bottom: 30px;
  flex-wrap: wrap;
}
.tag {
  padding: 8px 16px;
  border: 1px solid #ddd;
  border-radius: 20px;
  cursor: pointer;
  transition: background-color 0.3s, color 0.3s;
  color: #444;
  font-size: 14px;
}
.tag.active {
  background-color: #4a90e2;
  color: white;
  border-color: #4a90e2;
}
.playlist-card {
  background: #ffffff;
  border-radius: 14px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
  margin-bottom: 30px;
  overflow: hidden;
  transition: transform 0.2s ease-in-out;
}
.playlist-card:hover {
  transform: translateY(-5px);
}
.playlist-header {
  display: flex;
  align-items: center;
  padding: 16px;
}
.playlist-cover {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  margin-right: 16px;
  border: 2px solid #f0f8ff;
}
.playlist-info h3 {
  font-size: 20px;
  color: #333;
  margin-bottom: 8px;
}
.playlist-meta {
  color: #777;
  font-size: 14px;
  margin-bottom: 12px;
}
.playlist-tags {
  display: flex;
  gap: 8px;
  margin-bottom: 12px;
}
.playlist-tag {
  padding: 4px 12px;
  background-color: #f0f8ff;
  border-radius: 20px;
  font-size: 12px;
  color: #4a90e2;
}
.playlist-time {
  color: #999;
  font-size: 13px;
}
.modal {
  display: flex;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  justify-content: center;
  align-items: center;
  z-index: 1000;
}
.modal-content {
  background-color: #ffffff;
  padding: 30px;
  border-radius: 12px;
  width: 350px;
  box-shadow: 0 6px 18px rgba(0, 0, 0, 0.15);
}
.modal input,
.modal textarea,
.modal select {
  width: 100%;
  margin-bottom: 14px;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 14px;
  box-sizing: border-box;
  transition: border-color 0.3s;
}
.modal input:focus,
.modal textarea:focus,
.modal select:focus {
  border-color: #4a90e2;
  outline: none;
}
.modal button {
  background-color: #4a90e2;
  color: white;
  padding: 14px 20px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
  font-size: 16px;
  width: 100%;
}
.modal button:hover {
  background-color: #357ab7;
  transform: translateY(-2px);
}
@media (max-width: 768px) {
  .header {
    flex-direction: column;
    align-items: flex-start;
  }
  .tags {
    justify-content: flex-start;
  }
  .tag {
    font-size: 12px;
    padding: 4px 8px;
  }
  .playlist-header {
    flex-direction: column;
    align-items: flex-start;
  }
  .playlist-cover {
    margin-bottom: 12px;
  }
}
</style>