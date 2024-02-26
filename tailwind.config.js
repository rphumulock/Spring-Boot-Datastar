/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/main/resources/templates/**/*.html",
  ],
  theme: {
    extend: {},
  },
  daisyui: {
    themes: ["retro"],
  },
  plugins: [
    require("daisyui"),
  ],
}