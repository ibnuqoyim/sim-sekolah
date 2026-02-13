<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useAuthStore } from '../stores/auth';

const authStore = useAuthStore();
const profile = ref({
  name: '',
  address: '',
  contactEmail: '',
  contactPhone: '',
  logoUrl: ''
});
const message = ref('');
const error = ref('');

const API_URL = import.meta.env.VITE_API_BASE_URL.replace('/auth/', '/school/');

const fetchProfile = async () => {
  try {
    const response = await axios.get(API_URL + 'profile', {
      headers: { Authorization: 'Bearer ' + authStore.token }
    });
    profile.value = response.data;
  } catch (err) {
    error.value = 'Failed to fetch profile';
  }
};

const updateProfile = async () => {
  try {
    message.value = '';
    error.value = '';
    const response = await axios.put(API_URL + 'profile', profile.value, {
      headers: { Authorization: 'Bearer ' + authStore.token }
    });
    profile.value = response.data;
    message.value = 'Profile updated successfully!';
  } catch (err) {
    error.value = 'Failed to update profile';
  }
};

onMounted(() => {
  fetchProfile();
});
</script>

<template>
  <div class="max-w-4xl mx-auto bg-white shadow-xl rounded-2xl overflow-hidden">
    <div class="bg-indigo-600 px-6 py-4">
      <h2 class="text-2xl font-bold text-white">School Profile</h2>
      <p class="text-indigo-100 text-sm">Manage your school's public information</p>
    </div>
    
    <div class="p-8">
      <div v-if="message" class="mb-6 p-4 bg-green-50 border-l-4 border-green-500 text-green-700 rounded-r shadow-sm">
        <div class="flex">
          <div class="flex-shrink-0">
            <svg class="h-5 w-5 text-green-400" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
              <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd" />
            </svg>
          </div>
          <div class="ml-3">
            <p class="text-sm font-medium">{{ message }}</p>
          </div>
        </div>
      </div>

      <div v-if="error" class="mb-6 p-4 bg-red-50 border-l-4 border-red-500 text-red-700 rounded-r shadow-sm">
        <div class="flex">
          <div class="flex-shrink-0">
            <svg class="h-5 w-5 text-red-400" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
              <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clip-rule="evenodd" />
            </svg>
          </div>
          <div class="ml-3">
            <p class="text-sm font-medium">{{ error }}</p>
          </div>
        </div>
      </div>

      <form @submit.prevent="updateProfile" class="space-y-6">
        <div class="grid grid-cols-1 gap-y-6 gap-x-4 sm:grid-cols-6">
          <div class="sm:col-span-6">
            <label class="block text-sm font-medium text-gray-700">School Name</label>
            <div class="mt-1 flex rounded-md shadow-sm">
              <input v-model="profile.name" type="text" class="flex-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full min-w-0 rounded-md sm:text-sm border-gray-300 p-3 border" placeholder="Enter school name" required />
            </div>
          </div>

          <div class="sm:col-span-6">
            <label class="block text-sm font-medium text-gray-700">Address</label>
            <div class="mt-1">
              <textarea v-model="profile.address" rows="3" class="shadow-sm focus:ring-indigo-500 focus:border-indigo-500 block w-full sm:text-sm border border-gray-300 rounded-md p-3" placeholder="Enter full address"></textarea>
            </div>
          </div>

          <div class="sm:col-span-3">
            <label class="block text-sm font-medium text-gray-700">Contact Email</label>
            <div class="mt-1 relative rounded-md shadow-sm">
                <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <svg class="h-5 w-5 text-gray-400" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                    <path d="M2.003 5.884L10 9.882l7.997-3.998A2 2 0 0016 4H4a2 2 0 00-1.997 1.884z" />
                    <path d="M18 8.118l-8 4-8-4V14a2 2 0 002 2h12a2 2 0 002-2V8.118z" />
                  </svg>
                </div>
                <input v-model="profile.contactEmail" type="email" class="focus:ring-indigo-500 focus:border-indigo-500 block w-full pl-10 sm:text-sm border-gray-300 rounded-md p-3 border" placeholder="contact@school.com" />
            </div>
          </div>

          <div class="sm:col-span-3">
            <label class="block text-sm font-medium text-gray-700">Contact Phone</label>
            <div class="mt-1 relative rounded-md shadow-sm">
                <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <svg class="h-5 w-5 text-gray-400" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                    <path d="M2 3a1 1 0 011-1h2.153a1 1 0 01.986.836l.74 4.435a1 1 0 01-.54 1.06l-1.548.773a11.037 11.037 0 006.105 6.105l.774-1.548a1 1 0 011.059-.54l4.435.74a1 1 0 01.836.986V17a1 1 0 01-1 1h-2C7.82 18 2 12.18 2 5V3z" />
                  </svg>
                </div>
                <input v-model="profile.contactPhone" type="text" class="focus:ring-indigo-500 focus:border-indigo-500 block w-full pl-10 sm:text-sm border-gray-300 rounded-md p-3 border" placeholder="+1 (555) 987-6543" />
            </div>
          </div>
          
          <div class="sm:col-span-6">
              <label class="block text-sm font-medium text-gray-700">Logo URL</label>
              <div class="mt-1 relative rounded-md shadow-sm">
                   <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                      <svg class="h-5 w-5 text-gray-400" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                        <path fill-rule="evenodd" d="M4 3a2 2 0 00-2 2v10a2 2 0 002 2h12a2 2 0 002-2V5a2 2 0 00-2-2H4zm12 12H4l4-8 3 6 2-4 3 6z" clip-rule="evenodd" />
                      </svg>
                   </div>
                  <input v-model="profile.logoUrl" type="text" class="focus:ring-indigo-500 focus:border-indigo-500 block w-full pl-10 sm:text-sm border-gray-300 rounded-md p-3 border" placeholder="https://example.com/logo.png" />
              </div>
              <p class="mt-2 text-sm text-gray-500">Provide a direct URL to your school's logo image.</p>
          </div>
        </div>

        <div class="pt-5 border-t border-gray-200">
          <div class="flex justify-end">
            <button type="submit" class="ml-3 inline-flex justify-center py-2 px-6 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 transition-colors duration-200">
              Save Changes
            </button>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>
