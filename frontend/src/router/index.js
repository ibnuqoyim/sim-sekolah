import { createRouter, createWebHistory } from 'vue-router';
import LoginView from '../views/LoginView.vue';
import MainLayout from '../views/MainLayout.vue';
import { useAuthStore } from '../stores/auth';

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/login',
            name: 'login',
            component: LoginView
        },
        {
            path: '/',
            component: MainLayout,
            meta: { requiresAuth: true },
            children: [
                {
                    path: '',
                    name: 'home',
                    component: () => import('../views/HomeView.vue')
                },
                {
                    path: 'admin/school-profile',
                    name: 'admin-school-profile',
                    component: () => import('../views/AdminSchoolProfile.vue'),
                    meta: { requiresAdmin: true }
                },
                {
                    path: 'admin/users',
                    name: 'admin-users',
                    component: () => import('../views/AdminUserList.vue'),
                    meta: { requiresAdmin: true }
                },
                {
                    path: 'operator/teachers',
                    name: 'operator-teachers',
                    component: () => import('../views/OperatorTeacherList.vue'),
                    meta: { requiresOperator: true }
                },
                {
                    path: 'operator/students',
                    name: 'operator-students',
                    component: () => import('../views/OperatorStudentList.vue'),
                    meta: { requiresOperator: true }
                }
            ]
        }
    ]
});

router.beforeEach((to, from, next) => {
    const authStore = useAuthStore();
    const publicPages = ['/login'];
    const authRequired = !publicPages.includes(to.path);
    const loggedIn = authStore.user;

    if (authRequired && !loggedIn) {
        next('/login');
    } else if (to.meta.requiresAdmin && authStore.user?.role !== 'ADMIN') {
        next('/');
    } else if (to.meta.requiresOperator && authStore.user?.role !== 'OPERATOR' && authStore.user?.role !== 'ADMIN') {
        next('/');
    } else {
        next();
    }
});

export default router;
