<script setup>
import { useAuthStore } from '../stores/auth';
import { useRouter, useRoute } from 'vue-router';
import { computed } from 'vue';

const authStore = useAuthStore();
const router = useRouter();
const route = useRoute();

const logout = () => {
  authStore.logout();
  router.push('/login');
};

const isActive = (path) => route.path === path;
</script>

<template>
  <div class="min-h-screen bg-gray-50 font-sans">
    <nav class="bg-indigo-600 shadow-md">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex items-center justify-between h-16">
          <div class="flex items-center">
            <div class="flex-shrink-0">
               <svg class="h-8 w-8 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                   <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253" />
               </svg>
            </div>
            <span class="ml-3 font-bold text-xl text-white tracking-wider">SIM Sekolah</span>
            <div class="hidden md:block">
              <div class="ml-10 flex items-baseline space-x-4">
                <router-link to="/" :class="[isActive('/') ? 'bg-indigo-700 text-white' : 'text-indigo-100 hover:bg-indigo-500 hover:text-white', 'px-3 py-2 rounded-md text-sm font-medium transition-colors duration-200']">Dashboard</router-link>
                
                <router-link v-if="authStore.user?.role === 'ADMIN'" to="/admin/school-profile" :class="[isActive('/admin/school-profile') ? 'bg-indigo-700 text-white' : 'text-indigo-100 hover:bg-indigo-500 hover:text-white', 'px-3 py-2 rounded-md text-sm font-medium transition-colors duration-200']">School Profile</router-link>
                
                <router-link v-if="authStore.user?.role === 'ADMIN'" to="/admin/users" :class="[isActive('/admin/users') ? 'bg-indigo-700 text-white' : 'text-indigo-100 hover:bg-indigo-500 hover:text-white', 'px-3 py-2 rounded-md text-sm font-medium transition-colors duration-200']">Users</router-link>
                
                <router-link v-if="authStore.user?.role === 'OPERATOR' || authStore.user?.role === 'ADMIN'" to="/operator/teachers" :class="[isActive('/operator/teachers') ? 'bg-indigo-700 text-white' : 'text-indigo-100 hover:bg-indigo-500 hover:text-white', 'px-3 py-2 rounded-md text-sm font-medium transition-colors duration-200']">Teachers</router-link>
                
                <router-link v-if="authStore.user?.role === 'OPERATOR' || authStore.user?.role === 'ADMIN'" to="/operator/students" :class="[isActive('/operator/students') ? 'bg-indigo-700 text-white' : 'text-indigo-100 hover:bg-indigo-500 hover:text-white', 'px-3 py-2 rounded-md text-sm font-medium transition-colors duration-200']">Students</router-link>

                <router-link to="/agendas" :class="[isActive('/agendas') ? 'bg-indigo-700 text-white' : 'text-indigo-100 hover:bg-indigo-500 hover:text-white', 'px-3 py-2 rounded-md text-sm font-medium transition-colors duration-200']">Agendas</router-link>
                
                <router-link to="/announcements" :class="[isActive('/announcements') ? 'bg-indigo-700 text-white' : 'text-indigo-100 hover:bg-indigo-500 hover:text-white', 'px-3 py-2 rounded-md text-sm font-medium transition-colors duration-200']">Announcements</router-link>
              </div>
            </div>
          </div>
          <div>
            <div class="ml-4 flex items-center md:ml-6">
              <span class="text-indigo-100 text-sm mr-4">{{ authStore.user?.username }} <span class="text-xs bg-indigo-800 px-2 py-0.5 rounded-full">{{ authStore.user?.role }}</span></span>
              <button @click="logout" class="bg-indigo-700 p-1 rounded-full text-indigo-200 hover:text-white focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-indigo-600 focus:ring-white">
                <span class="sr-only">Logout</span>
                 <svg class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" />
                 </svg>
              </button>
            </div>
          </div>
        </div>
      </div>
    </nav>

    <header v-if="$route.meta.title" class="bg-white shadow">
      <div class="max-w-7xl mx-auto py-6 px-4 sm:px-6 lg:px-8">
        <h1 class="text-3xl font-bold text-gray-900">{{ $route.meta.title }}</h1>
      </div>
    </header>

    <main>
      <div class="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
        <router-view></router-view>
      </div>
    </main>
  </div>
</template>
