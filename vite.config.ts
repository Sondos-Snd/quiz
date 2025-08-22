import { defineConfig } from 'vite';

export default defineConfig({
  // other configurations...
  server: {
      host: true, // Try adding this

    allowedHosts: ['*'
    ]
  }
});