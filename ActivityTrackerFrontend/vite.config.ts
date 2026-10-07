import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  build: {
    outDir: "../activitytracker/src/main/resources/static/",//change build output directory relative to project root
    assetsDir:"vue-frontend",
    // rollupOptions:{
    //   output: {
    //     entryFileNames: 'frontend.bundle.js',  // JS files in "js" folder
    //     chunkFileNames: `frontend.js`,
    //     assetFileNames: 'frontend.[ext]',
    //   },
    // },
    emptyOutDir : true//will overwrite all content in directory
  }
})
