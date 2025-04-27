<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

// 响应式数据
const loginUsername = ref('');
const loginPassword = ref('');
const loginRememberMe = ref(false);
const loginPasswordStrengthText = ref('');
const loginPasswordStrengthColor = ref('');

const registerUsername = ref('');
const registerEmail = ref('');
const registerPassword = ref('');
const registerPasswordStrengthText = ref('');
const registerPasswordStrengthColor = ref('');

const forgotEmail = ref('');

// 控制面板显示
const showLoginPanel = ref(true);
const showRegisterPanel = ref(false);
const showForgotPanel = ref(false);

// 密码可见性
const loginPasswordVisible = ref(false);
const registerPasswordVisible = ref(false);

// Three.js相关变量
let scene, camera, renderer, particles;
let animationId = null;
const canvasContainerRef = ref(null);

// 方法
function handleLogin() {
  if (!loginUsername.value || !loginPassword.value) {
    alert('请填写用户名和密码');
    return;
  }
  
  // 实际项目中这里应该有API调用
  // 这里仅做演示，成功后跳转到首页
  router.push('/');
}

function handleRegister() {
  if (!registerUsername.value || !registerEmail.value || !registerPassword.value) {
    alert('请填写所有必填项');
    return;
  }
  
  alert(`注册成功！用户名：${registerUsername.value}，邮箱：${registerEmail.value}`);
  toggleLoginPanel();
}

function sendResetEmail() {
  if (!forgotEmail.value) {
    alert('请输入邮箱地址');
    return;
  }
  
  alert(`已向 ${forgotEmail.value} 发送重置邮件`);
  toggleLoginPanel();
}

function toggleLoginPanel() {
  showLoginPanel.value = true;
  showRegisterPanel.value = false;
  showForgotPanel.value = false;
}

function toggleRegisterPanel() {
  showLoginPanel.value = false;
  showRegisterPanel.value = true;
  showForgotPanel.value = false;
}

function toggleForgotPanel() {
  showLoginPanel.value = false;
  showRegisterPanel.value = false;
  showForgotPanel.value = true;
}

function toggleLoginPasswordVisibility() {
  loginPasswordVisible.value = !loginPasswordVisible.value;
}

function toggleRegisterPasswordVisibility() {
  registerPasswordVisible.value = !registerPasswordVisible.value;
}

function checkLoginPasswordStrength(password) {
  let strength = 0;
  if (password.length >= 8) strength++;
  if (/[A-Z]/.test(password)) strength++;
  if (/[a-z]/.test(password)) strength++;
  if (/[0-9]/.test(password)) strength++;
  if (/[!@#$%^&*(),.?":{}|<>]/.test(password)) strength++;

  if (strength < 2) {
    loginPasswordStrengthText.value = '密码强度弱';
    loginPasswordStrengthColor.value = 'red';
  } else if (strength < 4) {
    loginPasswordStrengthText.value = '密码强度中等';
    loginPasswordStrengthColor.value = 'yellow';
  } else {
    loginPasswordStrengthText.value = '密码强度强';
    loginPasswordStrengthColor.value = 'green';
  }
}

function checkRegisterPasswordStrength(password) {
  let strength = 0;
  if (password.length >= 8) strength++;
  if (/[A-Z]/.test(password)) strength++;
  if (/[a-z]/.test(password)) strength++;
  if (/[0-9]/.test(password)) strength++;
  if (/[!@#$%^&*(),.?":{}|<>]/.test(password)) strength++;

  if (strength < 2) {
    registerPasswordStrengthText.value = '密码强度弱';
    registerPasswordStrengthColor.value = 'red';
  } else if (strength < 4) {
    registerPasswordStrengthText.value = '密码强度中等';
    registerPasswordStrengthColor.value = 'yellow';
  } else {
    registerPasswordStrengthText.value = '密码强度强';
    registerPasswordStrengthColor.value = 'green';
  }
}

// Three.js相关方法
function initParticles() {
  // 确保全局THREE对象已加载
  if (typeof THREE === 'undefined') {
    console.error('THREE.js not loaded');
    return;
  }

  const container = canvasContainerRef.value;
  if (!container) {
    console.error('Canvas container not found');
    return;
  }

  scene = new THREE.Scene();
  camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
  
  // 使用透明背景
  renderer = new THREE.WebGLRenderer({ 
    alpha: true,
    antialias: true 
  });
  renderer.setSize(window.innerWidth, window.innerHeight);
  container.appendChild(renderer.domElement);

  // 创建粒子几何体
  const geometry = new THREE.BufferGeometry();
  const vertices = [];
  for (let i = 0; i < 5000; i++) {
    vertices.push(
      Math.random() * 2000 - 1000,
      Math.random() * 2000 - 1000,
      Math.random() * 2000 - 1000
    );
  }
  geometry.setAttribute('position', new THREE.Float32BufferAttribute(vertices, 3));

  // 粒子材质 - 更亮的蓝色
  const material = new THREE.PointsMaterial({
    size: 3,
    color: 0xA2D5F2,
    transparent: true,
    opacity: 0.7,
    blending: THREE.AdditiveBlending
  });

  particles = new THREE.Points(geometry, material);
  scene.add(particles);
  camera.position.z = 1000;

  // 鼠标交互
  document.addEventListener('mousemove', handleMouseMove);
}

function handleMouseMove(e) {
  if (particles) {
    particles.rotation.x = e.clientY * 0.0001;
    particles.rotation.y = e.clientX * 0.0001;
  }
}

function animate() {
  animationId = requestAnimationFrame(animate);
  if (particles) {
    particles.rotation.y += 0.0001;
  }
  if (renderer && scene && camera) {
    renderer.render(scene, camera);
  }
}

function handleWindowResize() {
  if (camera && renderer) {
    camera.aspect = window.innerWidth / window.innerHeight;
    camera.updateProjectionMatrix();
    renderer.setSize(window.innerWidth, window.innerHeight);
  }
}

// 生命周期钩子
onMounted(() => {
  // 延迟一帧初始化Three.js，确保DOM已完全挂载
  setTimeout(() => {
    initParticles();
    animate();
  }, 0);
  
  // 窗口大小调整
  window.addEventListener('resize', handleWindowResize);
});

onUnmounted(() => {
  // 清理Three.js资源
  if (animationId) {
    cancelAnimationFrame(animationId);
  }
  
  document.removeEventListener('mousemove', handleMouseMove);
  window.removeEventListener('resize', handleWindowResize);
  
  if (renderer) {
    renderer.dispose();
    if (canvasContainerRef.value && canvasContainerRef.value.firstChild) {
      canvasContainerRef.value.removeChild(canvasContainerRef.value.firstChild);
    }
  }
  
  if (scene) {
    // 清理场景中的所有对象
    while(scene.children.length > 0) { 
      scene.remove(scene.children[0]); 
    }
  }
});
</script>

<template>
  <div>
    <div id="canvas-container" ref="canvasContainerRef"></div>

    <div class="panel-container">
      <!-- 登录面板 -->
      <div class="panel" :class="{ 'hidden': !showLoginPanel }">
        <h2 class="text-center text-cyan-700">Sound Therapy</h2>
        <input 
          type="text" 
          class="login-input" 
          placeholder="Username" 
          v-model="loginUsername" 
          required
        >
        <div class="relative">
          <input 
            :type="loginPasswordVisible ? 'text' : 'password'" 
            class="login-input" 
            placeholder="Password" 
            v-model="loginPassword" 
            @input="checkLoginPasswordStrength(loginPassword)" 
            required
          >
          <i 
            class="password-visibility fa-solid"
            :class="loginPasswordVisible ? 'fa-eye-slash' : 'fa-eye'" 
            @click="toggleLoginPasswordVisibility"
          ></i>
        </div>
        <div 
          class="password-strength" 
          :style="{ color: loginPasswordStrengthColor }"
        >{{ loginPasswordStrengthText }}</div>
        <div class="remember-me">
          <input 
            type="checkbox" 
            id="login-remember-me" 
            v-model="loginRememberMe"
          >
          <label for="login-remember-me" class="ml-2">记住我</label>
        </div>
        <button class="login-btn" @click="handleLogin">Harmony Login</button>
        <a href="#" class="forgot-password" @click.prevent="toggleForgotPanel">忘记密码?</a>
        <a href="#" class="register-link" @click.prevent="toggleRegisterPanel">立即注册</a>
      </div>

      <!-- 注册面板 -->
      <div class="panel" :class="{ 'hidden': !showRegisterPanel }">
        <h2 class="text-center text-cyan-700">注册账号</h2>
        <input 
          type="text" 
          class="login-input" 
          placeholder="Username" 
          v-model="registerUsername" 
          required
        >
        <input 
          type="email" 
          class="login-input" 
          placeholder="Email" 
          v-model="registerEmail" 
          required
        >
        <div class="relative">
          <input 
            :type="registerPasswordVisible ? 'text' : 'password'" 
            class="login-input" 
            placeholder="Password" 
            v-model="registerPassword" 
            @input="checkRegisterPasswordStrength(registerPassword)" 
            required
          >
          <i 
            class="password-visibility fa-solid"
            :class="registerPasswordVisible ? 'fa-eye-slash' : 'fa-eye'" 
            @click="toggleRegisterPasswordVisibility"
          ></i>
        </div>
        <div 
          class="password-strength" 
          :style="{ color: registerPasswordStrengthColor }"
        >{{ registerPasswordStrengthText }}</div>
        <button class="login-btn" @click="handleRegister">注册</button>
        <a href="#" class="login-link" @click.prevent="toggleLoginPanel">返回登录</a>
      </div>

      <!-- 忘记密码面板 -->
      <div class="panel" :class="{ 'hidden': !showForgotPanel }">
        <h2 class="text-center text-cyan-700">找回密码</h2>
        <input 
          type="email" 
          class="login-input" 
          placeholder="输入邮箱地址" 
          v-model="forgotEmail" 
          required
        >
        <button class="login-btn" @click="sendResetEmail">发送重置邮件</button>
        <button class="login-btn mt-3" @click="toggleLoginPanel">返回登录</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 变量定义 */
:root {
  --main-gradient: linear-gradient(135deg, #8EC5FC 0%, #e5d1ff 100%);
  --glass-bg: rgba(255, 255, 255, 0.15);
}

/* 全局样式 */
#app {
  margin: 0;
  overflow: hidden;
  font-family: 'Inter', sans-serif;
}

/* 为组件添加背景 */
div:first-child {
  margin: 0;
  min-height: 100vh;
  background: linear-gradient(135deg, #8EC5FC 0%, #e5d1ff 100%);
  overflow: hidden;
}

/* 粒子背景容器 */
#canvas-container {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  z-index: 0;
}

/* 面板容器 */
.panel-container {
  position: relative;
  width: 400px;
  margin: 20vh auto;
  perspective: 1000px;
  z-index: 1;
}

/* 面板样式 */
.panel {
  position: absolute;
  width: 100%;
  padding: 40px;
  background: rgba(255, 255, 255, 0.15);
  backdrop-filter: blur(12px);
  -webkit-backdrop-filter: blur(12px);
  border-radius: 20px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  transition: transform 0.6s;
  backface-visibility: hidden;
}

.panel.hidden {
  transform: rotateY(180deg);
  pointer-events: none;
}

/* 输入框样式 */
.login-input {
  width: 100%;
  padding: 15px;
  margin: 15px 0;
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 8px;
  transition: 0.3s;
  color: white;
}

.login-input::placeholder {
  color: rgba(255, 255, 255, 0.7);
}

.login-input:focus {
  outline: none;
  box-shadow: 0 0 15px rgba(74, 144, 226, 0.3);
}

/* 按钮样式 */
.login-btn {
  width: 100%;
  padding: 15px;
  background: linear-gradient(to right, #4A90E2, #6A5ACD);
  border: none;
  border-radius: 8px;
  color: white;
  cursor: pointer;
  transition: 0.3s;
  margin-top: 10px;
}

.login-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(74, 144, 226, 0.4);
}

/* 密码强度指示器 */
.password-strength {
  margin-top: -10px;
  margin-bottom: 15px;
  font-size: 0.9rem;
  color: #fff;
}

/* 密码可见性按钮 */
.password-visibility {
  position: absolute;
  right: 20px;
  top: 50%;
  transform: translateY(-50%);
  cursor: pointer;
  color: white;
}

/* 记住我复选框 */
.remember-me {
  display: flex;
  align-items: center;
  margin: 15px 0;
  color: #fff;
}

/* 链接样式 */
.forgot-password, .register-link, .login-link {
  display: block;
  margin-top: 15px;
  color: #fff;
  text-align: center;
  text-decoration: underline;
  cursor: pointer;
  transition: color 0.3s;
}

.forgot-password:hover, .register-link:hover, .login-link:hover {
  color: #bae0ff;
}

/* 标题样式 */
h2 {
  color: white !important;
  margin-bottom: 1rem;
  font-size: 1.75rem;
  font-weight: 700;
  text-align: center;
}

/* 相对定位容器 */
.relative {
  position: relative;
}

/* 间距 */
.mt-3 {
  margin-top: 0.75rem;
}

.ml-2 {
  margin-left: 0.5rem;
}

/* 响应式设计 */
@media (max-width: 500px) {
  .panel-container {
    width: 90%;
    margin: 10vh auto;
  }
  
  .panel {
    padding: 30px;
  }
  
  .login-input, .login-btn {
    padding: 12px;
  }
}
</style>