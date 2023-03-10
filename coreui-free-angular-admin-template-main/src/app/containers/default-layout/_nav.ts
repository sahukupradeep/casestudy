import { INavData } from '@coreui/angular';

export const navAdminItems: INavData[] = [
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
  },
  {
    name: 'Report',
    url: '/report',
    iconComponent: { name: 'cil-star' }
  }
];

export const navUserItems: INavData[] = [
  {
    name: 'Dashboard',
    url: '/dashboard',
    iconComponent: { name: 'cil-speedometer' }
  }
];
