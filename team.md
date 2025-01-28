---
layout: home
---
<script setup>
import { VPTeamMembers } from 'vitepress/theme'

const members = [
  {
    avatar: 'https://avatars.githubusercontent.com/u/75510490?v=4',
    name: 'st0x0ef',
    title: 'Creator, Developer',
    links: [
      { icon: 'github', link: 'https://github.com/yyx990803' }
    ]
  },
  {
    avatar: 'https://avatars.githubusercontent.com/u/67022946?s=400&u=4de78465366b69e735574576629f2fa0e59b8417&v=4',
    name: 'TATHAN',
    title: 'Co-Creator, Developer',
    links: [
      { icon: 'github', link: 'https://github.com/yyx990803' }
    ]
  },
  {
    avatar: 'https://avatars.githubusercontent.com/u/101066376?v=4',
    name: 'Fej',
    title: 'Developer',
    links: [
      { icon: 'github', link: 'https://github.com/Fej1Developer' }
    ]
  },
  {
    avatar: 'https://avatars.githubusercontent.com/u/156406024?v=4',
    name: 'Miyaiho',
    title: 'Developer',
    links: [
      { icon: 'github', link: 'https://github.com/miyaiho' }
    ]
  },

  {
    avatar: 'https://avatars.githubusercontent.com/u/163727888?v=4',
    name: 'Lukeon',
    title: 'Developer',
    links: [
      { icon: 'github', link: 'https://github.com/lukeon214' }
    ]
  },
  {
    avatar: 'https://avatars.githubusercontent.com/u/139490373?v=4',
    name: 'Eirmax',
    title: 'Developer',
    links: [
      { icon: 'github', link: 'https://github.com/eirmax' }
    ]
  },
  {
    avatar: 'https://avatars.githubusercontent.com/u/70532030?v=4',
    name: 'MincraftEinstein',
    title: 'Developer',
    links: [
      { icon: 'github', link: 'https://github.com/MincraftEinstein' }
    ]
  },
  {
    avatar: 'https://avatars.githubusercontent.com/u/120266450?v=4',
    name: 'Okamiz',
    title: 'Developer, Texturer',
    links: [
      { icon: 'github', link: 'https://github.com/Okamomille' }
    ]
  },
  {
    avatar: 'https://avatars.githubusercontent.com/u/153344018?v=4',
    name: 'Small Chubby',
    title: 'Developer',
    links: [
      { icon: 'github', link: 'https://github.com/GityBoyy' }
    ]
  },

  {
    avatar: 'https://avatars.githubusercontent.com/u/121094383?v=4',
    name: 'Storik',
    title: 'Texturer/Modeler',
    links: [
      { icon: 'github', link: 'https://github.com/e-storik' }
    ]
  },
  {
    avatar: 'https://cdn.discordapp.com/avatars/431150651729510410/24112a7633ecfe40975d29027789f313?size=1024',
    name: 'SpaceStar',
    title: 'Builder'
  },
  {
    avatar: 'https://cdn.discordapp.com/avatars/1003010832365539348/3e9ceaf9866d84927477a42a136d9825?size=1024',
    name: 'Kurjiano',
    title: 'Builder'
  },  
  {
    avatar: 'https://cdn.discordapp.com/avatars/667758839625416720/f2ed80b9c10b8d3528aec7c09ff9ec6e?size=1024',
    name: 'Ilyuxadwa',
    title: 'Texturer/Modeler'
  },
  {
    avatar: 'https://cdn.discordapp.com/avatars/719659781152833547/3e26b3495336a3163de22592c6fbc35d?size=1024',
    name: 'Dipdoop',
    title: 'Community Manager'
  } 
]
</script>

# Our Team

Say hello to our awesome team members who make this all possible.

<VPTeamMembers size="small" :members="members" />
