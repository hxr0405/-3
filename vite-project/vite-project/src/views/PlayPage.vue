<script>
import { ref, reactive, onMounted, onBeforeUnmount } from 'vue';

export default {
  name: 'PlayPage',
  setup() {
    // 治愈文字库
    const healingMessages = [
      "让音乐洗涤你的心灵",
      "每个音符都是治愈的良药",
      "闭上眼睛，感受旋律的流动",
      "音乐是心灵的最佳伴侣",
      "让旋律带走所有烦恼",
      "此刻，只有你和音乐",
      "深呼吸，让音乐充满你的身体",
      "音乐是无需翻译的宇宙语言",
      "每个节拍都是生命的律动",
      "让心灵随着音乐起舞",
      "音乐是治愈灵魂的良方",
      "旋律中藏着无限可能",
      "静下心来，聆听内心的声音",
      "音乐是黑暗中的一束光",
      "让音乐带你飞翔",
      "每个音符都是情感的诉说",
      "音乐是无需药物的治疗",
      "旋律是心灵的回声",
      "让音乐抚平内心的波澜",
      "此刻，沉浸在音乐的海洋中"
    ];
    // 主题库
    const themes = [
      { name: "清晨薄雾", bg: "linear-gradient(135deg, #a8edea, #fed6e3)", accent: "#5FD3F3" },
      { name: "薄荷清新", bg: "linear-gradient(135deg, #a1ffce, #faffd1)", accent: "#4CE0B3" },
      { name: "淡紫梦境", bg: "linear-gradient(135deg, #e0c3fc, #8ec5fc)", accent: "#B388FF" },
      { name: "粉红晨曦", bg: "linear-gradient(135deg, #ffcce7, #d4f0ff)", accent: "#FF80AB" },
      { name: "海洋微风", bg: "linear-gradient(135deg, #89f7fe, #66a6ff)", accent: "#40C4FF" },
      { name: "春日花园", bg: "linear-gradient(135deg, #c3f9ff, #f6d5f7)", accent: "#69E0D3" },
      { name: "棉花糖云", bg: "linear-gradient(135deg, #f5f7fa, #c3cfe2)", accent: "#A5B4FC" },
      { name: "柠檬阳光", bg: "linear-gradient(135deg, #f6d365, #fda085)", accent: "#FFB74D" },
      { name: "薰衣草田", bg: "linear-gradient(135deg, #d4a5ff, #a5b8ff)", accent: "#9575CD" },
      { name: "碧波荡漾", bg: "linear-gradient(135deg, #84fab0, #8fd3f4)", accent: "#4DB6AC" }
    ];

    // refs & state
    const isPlaying = ref(false);
    const isFavorited = ref(false);
    const currentThemeIndex = ref(Math.floor(Math.random() * themes.length));
    const healingText = ref('"让音乐洗涤你的心灵"');
    const albumArtUrl = ref(`https://source.unsplash.com/random/600x600/?music,album,nature&t=${Date.now()}`);
    const audio = new Audio('https://file-examples.com/storage/fe70d866a67dc89d3268769/2017/11/file_example_MP3_700KB.mp3');
    audio.preload = 'auto';
    audio.volume = 0.7;
    const volume = ref(70);
    const timeCurrent = ref('00:00');
    const timeTotal = ref('03:45');
    const progressPercent = ref(0);
    let textChangeInterval = null;
    let albumArtInterval = null;
    let audioContext = null;
    let analyser = null;
    let dataArray = null;
    let visualizerBars = [];
    let visualizerAnimationId = null;

    // 粒子效果
    const initParticles = () => {
      const particlesContainer = document.getElementById('particles');
      if (!particlesContainer) return;
      particlesContainer.innerHTML = '';
      const particleCount = 30;
      for (let i = 0; i < particleCount; i++) {
        const particle = document.createElement('div');
        particle.classList.add('particle');
        const size = Math.random() * 5 + 1;
        const posX = Math.random() * window.innerWidth;
        const posY = Math.random() * window.innerHeight;
        const delay = Math.random() * 5;
        const duration = Math.random() * 10 + 5;
        particle.style.width = `${size}px`;
        particle.style.height = `${size}px`;
        particle.style.left = `${posX}px`;
        particle.style.top = `${posY}px`;
        particle.style.animation = `float ${duration}s ease-in-out ${delay}s infinite`;
        particle.style.opacity = Math.random() * 0.6 + 0.1;
        particlesContainer.appendChild(particle);
      }
    };

    // 音频可视化
    const initVisualizer = () => {
      const visualizer = document.getElementById('visualizer');
      if (!visualizer) return;
      audioContext = new (window.AudioContext || window.webkitAudioContext)();
      analyser = audioContext.createAnalyser();
      analyser.fftSize = 256;
      const source = audioContext.createMediaElementSource(audio);
      source.connect(analyser);
      analyser.connect(audioContext.destination);
      const bufferLength = analyser.frequencyBinCount;
      dataArray = new Uint8Array(bufferLength);
      visualizer.innerHTML = '';
      visualizerBars = [];
      const barCount = 50;
      for (let i = 0; i < barCount; i++) {
        const bar = document.createElement('div');
        bar.classList.add('visualizer-bar');
        visualizer.appendChild(bar);
        visualizerBars.push(bar);
      }
      if (isPlaying.value) {
        updateVisualizer();
      }
    };
    const updateVisualizer = () => {
      if (!isPlaying.value) return;
      analyser.getByteFrequencyData(dataArray);
      const barCount = visualizerBars.length;
      const step = Math.floor(dataArray.length / barCount);
      for (let i = 0; i < barCount; i++) {
        const value = dataArray[i * step] / 255;
        const height = value * 100 + 10;
        visualizerBars[i].style.height = `${height}px`;
        visualizerBars[i].style.opacity = value * 0.8 + 0.2;
      }
      visualizerAnimationId = requestAnimationFrame(updateVisualizer);
    };

    // 治愈文字
    const updateHealingText = () => {
      const el = document.getElementById('healing-text');
      if (!el) return;
      el.style.opacity = '0';
      setTimeout(() => {
        const randomIndex = Math.floor(Math.random() * healingMessages.length);
        const randomMessage = healingMessages[randomIndex];
        healingText.value = `"${randomMessage}"`;
        el.textContent = healingText.value;
        el.style.opacity = '0.9';
      }, 500);
    };

    // 主题
    const setTheme = (index) => {
      const theme = themes[index];
      document.documentElement.style.setProperty('--accent-color', theme.accent);
      const themeBg = document.getElementById('theme-bg');
      if (themeBg) themeBg.style.background = theme.bg;
      const changeThemeBtn = document.getElementById('change-theme');
      if (changeThemeBtn) {
        changeThemeBtn.setAttribute('data-theme', theme.name);
        changeThemeBtn.setAttribute('title', `当前主题: ${theme.name} (点击更换)`);
      }
    };
    const changeTheme = () => {
      currentThemeIndex.value = (currentThemeIndex.value + 1) % themes.length;
      setTheme(currentThemeIndex.value);
      const changeThemeBtn = document.getElementById('change-theme');
      if (changeThemeBtn) {
        changeThemeBtn.style.transform = 'scale(1.2)';
        setTimeout(() => {
          changeThemeBtn.style.transform = 'scale(1)';
        }, 300);
      }
    };

    // 播放/暂停
    const togglePlayPause = () => {
      if (isPlaying.value) {
        audio.pause();
        isPlaying.value = false;
        const playBtn = document.getElementById('play-btn');
        if (playBtn) playBtn.classList.replace('fa-pause', 'fa-play');
        const albumArt = document.querySelector('.album-art');
        if (albumArt) albumArt.style.animation = 'none';
      } else {
        audio.play().then(() => {
          isPlaying.value = true;
          const playBtn = document.getElementById('play-btn');
          if (playBtn) playBtn.classList.replace('fa-play', 'fa-pause');
          const albumArt = document.querySelector('.album-art');
          if (albumArt) albumArt.style.animation = 'colorShift 20s linear infinite';
          if (!audioContext) {
            initVisualizer();
          } else {
            updateVisualizer();
          }
        }).catch(error => {
          alert('音频播放失败，请检查网络连接或音频文件');
        });
      }
    };

    // 收藏
    const toggleFavorite = () => {
      isFavorited.value = !isFavorited.value;
      const favoriteBtn = document.getElementById('favorite-btn');
      if (favoriteBtn) {
        favoriteBtn.classList.toggle('favorited');
        favoriteBtn.classList.toggle('fa-regular');
        favoriteBtn.classList.toggle('fa-solid');
        favoriteBtn.style.transform = 'scale(1.2)';
        setTimeout(() => {
          favoriteBtn.style.transform = 'scale(1)';
        }, 200);
      }
    };

    // 音量
    const updateVolume = (e) => {
      audio.volume = volume.value / 100;
      const volumeIcon = document.querySelector('.volume-icon');
      if (audio.volume === 0) {
        volumeIcon.classList.replace('fa-volume-high', 'fa-volume-off');
        volumeIcon.classList.replace('fa-volume-low', 'fa-volume-off');
      } else if (audio.volume < 0.5) {
        volumeIcon.classList.replace('fa-volume-high', 'fa-volume-low');
        volumeIcon.classList.replace('fa-volume-off', 'fa-volume-low');
      } else {
        volumeIcon.classList.replace('fa-volume-low', 'fa-volume-high');
        volumeIcon.classList.replace('fa-volume-off', 'fa-volume-high');
      }
    };

    // 时间
    const updateTime = () => {
      const currentMinutes = Math.floor(audio.currentTime / 60);
      const currentSeconds = Math.floor(audio.currentTime % 60);
      timeCurrent.value = `${String(currentMinutes).padStart(2, '0')}:${String(currentSeconds).padStart(2, '0')}`;
      const durationMinutes = Math.floor(audio.duration / 60);
      const durationSeconds = Math.floor(audio.duration % 60);
      timeTotal.value = `${String(durationMinutes).padStart(2, '0')}:${String(durationSeconds).padStart(2, '0')}`;
      progressPercent.value = (audio.currentTime / audio.duration) * 100;
    };

    // 进度
    const setProgress = (e) => {
      const width = e.currentTarget.clientWidth;
      const clickX = e.offsetX;
      const duration = audio.duration;
      audio.currentTime = (clickX / width) * duration;
    };

    // 涟漪
    const createRipple = (e) => {
      const ripple = document.createElement('span');
      ripple.classList.add('ripple');
      const rect = e.target.getBoundingClientRect();
      const size = Math.max(rect.width, rect.height);
      ripple.style.width = `${size}px`;
      ripple.style.height = `${size}px`;
      ripple.style.left = `${e.clientX - rect.left - size/2}px`;
      ripple.style.top = `${e.clientY - rect.top - size/2}px`;
      e.target.appendChild(ripple);
      setTimeout(() => {
        ripple.remove();
      }, 600);
    };

    // 键盘快捷键
    const handleKeydown = (e) => {
      switch(e.code) {
        case 'Space':
          e.preventDefault();
          togglePlayPause();
          break;
        case 'ArrowUp':
          audio.volume = Math.min(audio.volume + 0.1, 1);
          volume.value = audio.volume * 100;
          updateVolume();
          break;
        case 'ArrowDown':
          audio.volume = Math.max(audio.volume - 0.1, 0);
          volume.value = audio.volume * 100;
          updateVolume();
          break;
        case 'ArrowRight':
          audio.currentTime = Math.min(audio.currentTime + 5, audio.duration);
          break;
        case 'ArrowLeft':
          audio.currentTime = Math.max(audio.currentTime - 5, 0);
          break;
        case 'KeyM':
          audio.volume = audio.volume > 0 ? 0 : 0.7;
          volume.value = audio.volume * 100;
          updateVolume();
          break;
        case 'KeyT':
          changeTheme();
          break;
      }
    };

    // 初始化
    onMounted(() => {
      setTheme(currentThemeIndex.value);
      initParticles();
      updateHealingText();
      textChangeInterval = setInterval(updateHealingText, 15000);
      albumArtInterval = setInterval(() => {
        albumArtUrl.value = `https://source.unsplash.com/random/600x600/?music,album,nature&t=${Date.now()}`;
      }, 30000);
      document.addEventListener('keydown', handleKeydown);
      audio.addEventListener('timeupdate', updateTime);
      audio.addEventListener('ended', () => {
        isPlaying.value = false;
        const playBtn = document.getElementById('play-btn');
        if (playBtn) playBtn.classList.replace('fa-pause', 'fa-play');
        const albumArt = document.querySelector('.album-art');
        if (albumArt) albumArt.style.animation = 'none';
      });
      audio.addEventListener('loadedmetadata', updateTime);
      // 初始化音量
      updateVolume();
    });
    onBeforeUnmount(() => {
      clearInterval(textChangeInterval);
      clearInterval(albumArtInterval);
      document.removeEventListener('keydown', handleKeydown);
      if (visualizerAnimationId) cancelAnimationFrame(visualizerAnimationId);
      if (audioContext) audioContext.close();
    });

    return {
      isPlaying,
      isFavorited,
      currentThemeIndex,
      healingText,
      albumArtUrl,
      volume,
      timeCurrent,
      timeTotal,
      progressPercent,
      togglePlayPause,
      toggleFavorite,
      updateVolume,
      setProgress,
      changeTheme,
      createRipple,
      themes
    };
  }
};
</script>

<template>
  <div>
    <div class="theme-bg" id="theme-bg"></div>
    <div class="theme-overlay"></div>
    <div class="particles" id="particles"></div>
    <div class="visualizer" id="visualizer"></div>
    <header>
      <div class="logo">Harmony</div>
      <p class="healing-text" id="healing-text">{{ healingText }}</p>
      <div class="header-icons">
        <i class="icon fa-solid fa-palette" id="change-theme" :title="`当前主题: ${themes[currentThemeIndex]?.name} (点击更换)`" @click="changeTheme"></i>
        <i class="icon fa-solid fa-sliders" title="设置"></i>
        <i class="icon fa-solid fa-repeat" title="循环模式"></i>
      </div>
    </header>
    <main>
      <img :src="albumArtUrl" alt="专辑封面" class="album-art">
      <div class="song-info">
        <h1 class="song-title">宁静的海洋</h1>
        <p class="artist">治愈心灵乐团</p>
      </div>
      <div class="play-time">
        <span class="time-current">{{ timeCurrent }}</span>
        <div class="progress-container" id="progress-container" @click="setProgress">
          <div class="progress-bar" id="progress-bar" :style="{ width: progressPercent + '%' }"></div>
        </div>
        <span class="time-total">{{ timeTotal }}</span>
      </div>
    </main>
    <div class="player-controls">
      <div class="volume-container">
        <i class="control-icon fa-solid fa-volume-high volume-icon" title="音量"></i>
        <input type="range" min="0" max="100" v-model="volume" class="volume-slider" id="volume-slider" title="音量调节" @input="updateVolume">
      </div>
      <div class="controls">
        <i class="control-icon fa-solid fa-backward-step" title="上一首"></i>
        <i class="control-icon main-control fa-solid" :class="isPlaying ? 'fa-pause' : 'fa-play'" title="播放/暂停" id="play-btn" @click="togglePlayPause" @mousedown="createRipple"></i>
        <i class="control-icon fa-solid fa-forward-step" title="下一首"></i>
      </div>
      <div>
        <i class="control-icon fa-regular fa-heart favorite-btn" :class="{ favorited: isFavorited, 'fa-solid': isFavorited, 'fa-regular': !isFavorited }" title="收藏" id="favorite-btn" @click="toggleFavorite"></i>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 复制播放面.html中<style>的全部内容，已在前面获取 */
:root {
    --primary-color: #6C63FF;
    --secondary-color: #8E6BEF;
    --accent-color: #5FD3F3;
    --danger-color: #FF6B6B;
    --transition-speed: 0.3s;
}
body {
    margin: 0;
    padding: 0;
    font-family: 'Poppins', 'Noto Sans SC', Arial, sans-serif;
    background: #f5f7fa;
    color: #272626;
    min-height: 100vh;
    overflow-x: hidden;
}
.theme-bg {
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    z-index: -2;
    background: linear-gradient(135deg, #a8edea, #fed6e3);
    transition: background 0.8s cubic-bezier(0.4, 0, 0.2, 1);
}
.theme-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    z-index: -1;
    background: rgba(255,255,255,0.5);
    pointer-events: none;
}
.particles {
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    z-index: 0;
    pointer-events: none;
}
.particle {
    position: absolute;
    border-radius: 50%;
    background: rgba(140, 140, 255, 0.2);
    pointer-events: none;
    animation: float 10s ease-in-out infinite;
}
@keyframes float {
    0% { transform: translateY(0) scale(1); }
    50% { transform: translateY(-40px) scale(1.2); }
    100% { transform: translateY(0) scale(1); }
}
.visualizer {
    position: absolute;
    bottom: 80px;
    left: 0;
    width: 100%;
    height: 100px;
    display: flex;
    justify-content: center;
    align-items: flex-end;
    gap: 3px;
    z-index: 1;
    opacity: 0.3;
}
.visualizer-bar {
    width: 4px;
    background: linear-gradient(to top, var(--accent-color), #8E6BEF);
    border-radius: 2px;
    transition: height 0.1s ease-out;
}
header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 2rem 3rem 1rem 3rem;
    background: transparent;
    position: relative;
    z-index: 2;
}
.logo {
    font-size: 2rem;
    font-weight: bold;
    color: var(--primary-color);
    letter-spacing: 2px;
    text-shadow: 0 2px 10px rgba(108, 99, 255, 0.1);
}
.healing-text {
    font-size: 1.3rem;
    color: var(--secondary-color);
    font-weight: 500;
    margin: 0 2rem;
    max-width: 400px;
    text-align: center;
    opacity: 0.9;
    transition: opacity 0.5s;
}
.header-icons {
    display: flex;
    gap: 2rem;
    align-items: center;
}
.icon {
    font-size: 1.5rem;
    color: var(--primary-color);
    cursor: pointer;
    transition: color 0.3s, transform 0.3s;
}
.icon:hover {
    color: var(--accent-color);
    transform: scale(1.1);
}
main {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    min-height: 60vh;
    position: relative;
    z-index: 2;
}
.album-art {
    width: 260px;
    height: 260px;
    border-radius: 30px;
    box-shadow: 0 10px 40px rgba(108, 99, 255, 0.15);
    object-fit: cover;
    margin-bottom: 2.5rem;
    transition: box-shadow 0.3s, transform 0.3s;
    animation: colorShift 20s linear infinite;
}
@keyframes colorShift {
    0% { filter: hue-rotate(0deg); }
    100% { filter: hue-rotate(360deg); }
}
.song-info {
    text-align: center;
    margin-bottom: 1.2rem;
}
.song-title {
    font-size: 2rem;
    font-weight: bold;
    color: var(--primary-color);
    margin: 0;
}
.artist {
    font-size: 1.1rem;
    color: var(--secondary-color);
    margin: 0.2rem 0 0 0;
}
.play-time {
    display: flex;
    align-items: center;
    gap: 1.2rem;
    margin: 1.5rem 0 2rem 0;
    width: 100%;
    max-width: 500px;
}
.time-current, .time-total {
    font-size: 1rem;
    color: #888;
    min-width: 48px;
    text-align: center;
}
.progress-container {
    flex: 1;
    height: 6px;
    background: rgba(140, 140, 255, 0.15);
    border-radius: 3px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}
.progress-bar {
    height: 100%;
    background: linear-gradient(to right, var(--accent-color), #8E6BEF);
    border-radius: 3px;
    width: 0;
    transition: width 0.2s;
}
.player-controls {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 2rem 3rem;
    gap: 2rem;
    background: rgba(255,255,255,0.7);
    border-radius: 30px;
    box-shadow: 0 -5px 20px rgba(0, 0, 0, 0.05);
}
.controls {
    display: flex;
    align-items: center;
    gap: 2rem;
}
.control-icon {
    font-size: 1.4rem;
    cursor: pointer;
    transition: all var(--transition-speed) ease;
    color: var(--secondary-color);
    position: relative;
}
.control-icon:hover {
    color: var(--primary-color);
    transform: scale(1.1);
}
.main-control {
    font-size: 2.5rem;
    background: linear-gradient(to right, var(--accent-color), #8E6BEF);
    -webkit-background-clip: text;
    background-clip: text;
    color: transparent;
    width: 70px;
    height: 70px;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 50%;
    border: 2px solid rgba(39, 38, 38, 0.2);
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
    transition: all 0.3s ease;
    position: relative;
    overflow: hidden;
}
.main-control:hover {
    transform: scale(1.1);
    box-shadow: 0 15px 40px rgba(0, 0, 0, 0.15);
    border-color: rgba(255, 255, 255, 0.3);
}
.main-control::after {
    content: '';
    position: absolute;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    background: radial-gradient(circle, rgba(255,255,255,0.2) 0%, rgba(255,255,255,0) 70%);
    opacity: 0;
    transition: opacity 0.3s ease;
}
.main-control:hover::after {
    opacity: 1;
}
.ripple {
    position: absolute;
    border-radius: 50%;
    background: rgba(26, 25, 25, 0.4);
    transform: scale(0);
    animation: ripple 0.6s linear;
    pointer-events: none;
}
@keyframes ripple {
    to {
        transform: scale(2.5);
        opacity: 0;
    }
}
.volume-container {
    position: relative;
    display: flex;
    align-items: center;
    gap: 0.8rem;
}
.volume-slider {
    -webkit-appearance: none;
    width: 100px;
    height: 4px;
    background: rgba(255, 255, 255, 0.2);
    outline: none;
    border-radius: 2px;
    transition: all var(--transition-speed) ease;
}
.volume-slider::-webkit-slider-thumb {
    -webkit-appearance: none;
    width: 14px;
    height: 14px;
    background: var(--primary-color);
    cursor: pointer;
    border-radius: 50%;
    transition: all 0.2s ease;
}
.volume-slider::-webkit-slider-thumb:hover {
    transform: scale(1.2);
    background: var(--accent-color);
}
.favorited {
    color: var(--danger-color) !important;
    text-shadow: 0 0 10px rgba(255, 107, 107, 0.5);
}
.theme-btn {
    background: rgba(255, 255, 255, 0.1);
    border: 1px solid rgba(255, 255, 255, 0.2);
    color: white;
    padding: 0.5rem 1rem;
    border-radius: 20px;
    cursor: pointer;
    transition: all 0.3s ease;
    backdrop-filter: blur(5px);
}
.theme-btn:hover {
    background: rgba(255, 255, 255, 0.2);
    transform: translateY(-2px);
}
@media (max-width: 768px) {
    header {
        padding: 1.2rem;
        flex-direction: column;
        gap: 1rem;
    }
    .healing-text {
        max-width: 90%;
        font-size: 1.2rem;
        margin: 0.5rem 0;
    }
    .header-icons {
        gap: 1rem;
    }
    .album-art {
        width: 220px;
        height: 220px;
        margin-bottom: 2rem;
    }
    .song-title {
        font-size: 1.6rem;
    }
    .artist {
        font-size: 1rem;
    }
    .progress-container {
        width: 90%;
    }
    .player-controls {
        padding: 1.2rem;
        flex-direction: column;
        gap: 1rem;
    }
    .controls {
        gap: 1.5rem;
    }
    .main-control {
        font-size: 2rem;
        width: 60px;
        height: 60px;
    }
    .visualizer {
        height: 80px;
        bottom: 70px;
    }
}
</style>