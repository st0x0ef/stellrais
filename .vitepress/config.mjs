import { defineConfig } from 'vitepress'

// https://vitepress.dev/reference/site-config
export default defineConfig({
  base: '/stellaris/',
  title: "Stellaris Wiki",
  description: "Learn how to create your own planets !",
  head: [['link', { rel: 'icon', href: '/stellaris/favicon.ico' }]],
  themeConfig: {
    // https://vitepress.dev/reference/default-theme-config
    nav: [
      { text: 'Home', link: '/' },
      { text: 'Documention', link: '/custom-planets' },
      { text: 'Wiki', link: '/wiki/installation' },
      { text: 'Team', link: '/team' }

    ],

    sidebar: [
      {
        text: 'Documentation',
        items: [
          { text: 'The Planet file', link: '/docs/custom-planets' },
          { text: 'The Screen files', link: '/docs/custom-screen' },
          { text: 'Custom Sky', link: '/docs/custom-sky' },
          { text: 'Custom Module', link: '/docs/custom-modules' }

        ]
      },
      {
        text: 'Wiki',
        items: [
          { text: 'Install the Mod', link: '/wiki/installation' },
          { text: 'Resources', link: '/wiki/resources' },

          { text: 'Oxygen', link: '/wiki/oxygen' },

          { text: 'Fuel', link: '/wiki/fuel' },
          { text: 'Electricity', link: '/wiki/electricity' },
          { text: 'Rocket', link: '/wiki/rocket' },
          { text: 'Machines', link: '/wiki/machines' },

          { text: 'FAQ', link: '/wiki/faq' }

        ]
      }
    ],

    socialLinks: [
      { icon: 'github', link: 'https://github.com/st0x0ef/stellaris' },
      { icon: 'youtube', link: 'https://www.youtube.com/channel/UCzrr9q1Afqu-Yb9i7nn1uPA' },
      { icon: 'discord', link: 'https://discord.gg/project-stellaris-698598471896268931' }

    ],

    footer: {
      message: 'Made by the Stellaris team with 💖.'
    }

  },
  markdown: {
    math: true
  }
})
