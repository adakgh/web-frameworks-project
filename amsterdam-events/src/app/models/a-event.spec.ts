import {AEvent, AEventStatus} from './a-event';

describe('AEvent', () => {
  it('should create an instance', () => {
    expect(new AEvent('some event', AEventStatus.DRAFT, new Date(2020, 9, 15)
      , true, new Date(2020, 10, 15)
      , 5, 'helloworld', 10)).toBeTruthy();
  });
});
