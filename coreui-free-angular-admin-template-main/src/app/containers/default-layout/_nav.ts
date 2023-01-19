import { INavData } from '@coreui/angular';

export const navItems: INavData[] = [
  {
    name: 'Dashboard',
    url: '/dashboard',
    iconComponent: { name: 'cil-speedometer' }
  },
  {
    name: 'User',
    iconComponent: { name: 'cil-star' },
    children: [
      {
        name: 'Search',
        url: '/user/search'
      },
      {
        name: 'Add',
        url: '/user/add'
      }
    ]
  }
];
