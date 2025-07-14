import { removeToken } from '@/utils/auth';
import { useRouter } from 'vue-router';

const router = useRouter();

const handleLogout = () => {
  removeToken();
  router.push('/login-component');
}; 