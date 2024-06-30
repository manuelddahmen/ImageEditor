%PARAMTYPE_SNAKE   Called by PARAMTYPE.

% (C) Copyright 1999-2010, All rights reserved.
% Cris Luengo, 18 September 2009.
% Based on PARAMTYPE_DATASET.
%
% 4 March 2010: Edited for compatability with DIPimage releases after 2.1.

function varargout = paramtype_snake(command,param,varargin)

switch command
   case 'control_create'
      fig = varargin{1};   % figure handle
      h = uicontrol(fig,...
                    'Style','edit',...
                    'String',param.default,...
                    'Visible','off',...
                    'HorizontalAlignment','left',...
                    'BackgroundColor',[1,1,1],...
                    'ButtonDownFcn','dipimage do_contextmenu dip_snake');
      if numel(varargin)>1
         index = varargin{2}; % item number in the dialog box (1 is topmost control, 0 is topmost label)
         set(h,'Tag',['control',num2str(index)]);
         % Since after DIPimage 2.1, the calling function sets the Tag,
         % and this function doesn't get a 4th argument.
      end
      cm = uicontextmenu('Parent',fig,'UserData',h);
      set(h,'UserData',struct('contextmenu',cm));
      varargout{1} = h;
   case 'control_value'
      varargout{2} = get(varargin{1},'String');
      if isempty(varargout{2})
         varargout{2} = '[]';
      end
      varargout{1} = evalin('base',varargout{2});
      if ~isa(varargout{1},'dip_snake')
         varargout{1} = dip_snake(varargout{1});
      end
   case 'default_value'
      try
         varargout{1} = evalin('base',param.default);
         if ~isa(varargout{1},'dip_snake')
            varargout{1} = dip_snake(varargout{1});
         end
      catch
         error('Default dip_snake evaluation failed.')
      end
   case 'definition_test'
      varargout{1} = '';
      if ~param.required
         if ~ischar(param.default)
            varargout{1} = 'DEFAULT dip_snake must be a string';
         end
      end
   case 'value_test'
      varargout{1} = '';
      value = varargin{1};
      if ~isa(value,'dip_snake') & ~isnumeric(value)
         varargout{1} = 'dip_snake expected';
      elseif nargout>1 & ~isa(value,'dip_snake')
         value = dip_snake(value); % Don't do this if it's not necessary.
      end
      varargout{2} = value;
end
