%DIP_SNAKE   Creates an object of class DIP_SNAKE
%   S = DIP_SNAKE(X) creats a DIP_SNAKE object from the
%   Nx2 array X. X(ii,:) are the coordinates of control
%   point ii. The snake S is always a closed contour.
%   N must be at least 5!
%
%   S = DIP_SNAKE(IMG), where IMG is a binary image,
%   creates a snake initialized to the boundary of the
%   first object in IMG.
%
%   S, by itself, plots the contour over the image in the
%   current figure. DISP(S,H) uses the figure with handle H
%   intead of the current figure. If H is the handle to a
%   line object, it updates the line rather than drawing a
%   new one. H = DISP(S) returns the handle to the line
%   object.
%
%   S = RESAMPLE(S,D) or S = S.RESAMPLE(D) creates new control
%   points along the snake S so that they are all a distance D
%   apart, using cubic interpolation between the points.
%   RESAMPLE(...,'linear') uses linear interpolation instead.
%
%   S(ii) returns the coordinates for control point ii.
%   S.X returns the x-coordinate for all control points.
%   S.Y returns the y-coordinate for all control points.
%
%   DIP_IMAGE(S) returns a binary image with the region
%   demarkated by the snake.
%
%   NOTE:
%     This object requires at least MATLAB version 7.6!
%
%   See also snakeminimize.

% (C) Copyright 2009-2010, All rights reserved.
% Cris Luengo, Uppsala, 18 September 2009.
%
% 4 March 2010: Added DIP_SNAKE(IMG) syntax, improved indexing, 
%               improved help.

classdef dip_snake
   properties
      x = []
      y = []
      imsz = [];
   end 
   methods

      % DIP_SNAKE   Constructor method
      function s = dip_snake(x)
         if nargin~=0
            if isa(x,'dip_image') || ( isnumeric(x) && all(size(x)>2) )
               x = +dip_image(x,'bin');
               s.imsz = imsize(x);
               data = dip_imagechaincode(x,2,1);
               cc = data.chain(2:end-1)+1;
               xc = [ 1; 1; 0;-1;-1;-1; 0; 1];
               yc = [ 0;-1;-1;-1; 0; 1; 1; 1];
               s.x = cumsum([data.start(1);xc(cc)]);
               s.y = cumsum([data.start(2);yc(cc)]);
            else
               if ~isnumeric(x)
                  error('Numeric inputs expected.')
               end
               if size(x,2)~=2 || size(x,1) < 5
                  error('The snake must be initialized with a Nx2 array, N>=5.')
               end
               x = double(x);
               s.x = x(:,1);
               s.y = x(:,2);
               s.imsz = ceil(max(x)+1);
            end
         end
      end

      function s = resample(s,d,mode)
      % RESAMPLE   Resamples the snake.
      %   S = RESAMPLE(S,D) or S = S.RESAMPLE(D) creates new control
      %   points along the snake S so that they are all a distance D
      %   apart, using cubic interpolation between the points.
      %   RESAMPLE(...,'linear') uses linear interpolation instead.
         if nargin<2
            d = 1;
         end
         if nargin<3
            mode = 'cubic';
         else
            mode = lower(mode);
            if ~any(strcmp(mode,{'linear','cubic'}))
               error('Illegal interpolation mode.');
            end
         end
         if strcmp(mode,'linear')
            r = 1; % for linear interpolation, replicate one value
         else
            r = 3; % for cubic spline interpolation, replicate 3 values
         end
         if length(s.x)<3
            error('Snake has too few control points.')
         end
         x = s.x; x = [x(end-r+1:end);x;x(1:r)];
         y = s.y; y = [y(end-r+1:end);y;y(1:r)];
         % p is the parameter along the snake
         p = [0;cumsum(sqrt( diff(x).^2 + diff(y).^2 ))];
         % the first control point should be at p=0
         p = p-p(r+1);
         % resample snake between first and last+1 control points
         x = interp1(p,x,0:d:p(end-r+1),mode);
         y = interp1(p,y,0:d:p(end-r+1),mode);
         % if the last new point is too close to the first one, remove it
         if norm([x(end),y(end)]-[x(1),y(1)]) < d/2
            x(end) = [];
            y(end) = [];
         end
         % ensure column vectors
         s.x = x(:);
         s.y = y(:);
         if length(s.x)<3
            error('Snake has become too small!')
         end
      end

      function oh = disp(s,h)
      % DISP   Plots the snake on top of an image.
      %   DISP(S) plots the snake on top of the image in the current
      %   figure. If the current figure is not displaying an image,
      %   an error is returned instead.
      %
      %   S, by itself, is the same as DISP(S)
      %
      %   DISP(S,H) uses the figure with handle H instead of the current
      %   figure. If H is the handle to a line object, it updates the line
      %   rather than drawing a new one.
      %
      %   H = DISP(S) returns the handle to the line object.
         if nargin<2
            h = gcf;
         end
         if ~ishandle(h)
            error('Figure does not exist!')
         end
         if strcmp(get(h,'type'),'line')
            lh = h;
            set(lh,'xdata',[s.x;s.x(1)],'ydata',[s.y;s.y(1)]);
         else
            if ~strcmp(get(h,'type'),'image')
               h = findobj(h,'type','image');
               if length(h)~=1
                  error('Cannot find an image in the figure.')
               end
            end
            h = get(h,'parent'); % axes handle
            os = get(h,'nextplot');
            set(h,'nextplot','add');
            lh = line([s.x;s.x(1)],[s.y;s.y(1)],'color',[0,0.8,0],'parent',h,'linewidth',1);
            set(h,'nextplot',os);
         end
         if nargout>0
            oh = lh;
         end
      end

      % DISPLAY   Overloaded method, calls DISP.
      function display(s)
         if strcmp(dipgetpref('DisplayToFigure'),'on')
            h = disp(s);
            h = get(get(h,'parent'),'parent');
            disp(['Displayed in figure ',num2str(h)])
         else
            if isequal(get(0,'FormatSpacing'),'compact')
               disp([inputname(1),' ='])
               disp(['dip_snake object with ',num2str(length(s.x)),' points.'])
            else
               disp(' ')
               disp([inputname(1),' ='])
               disp(' ')
               disp(['dip_snake object with ',num2str(length(s.x)),' points.'])
               disp(' ')
            end
         end
      end

      function l = length(s)
      % LENGTH   Overloaded method, returns number of control points.
         l = length(s.x);
      end

      function l = size(s)
      % SIZE   Overloaded method, returns size of DOUBLE(S).
         l = [length(s.x),2];
      end

      % SUBSREF   Overloaded method, allows indexing.
      function s = subsref(s,ind)
         if length(ind)==1 && strcmp(ind.type,'()')
            if length(ind.subs)~=1
               error('Illegal indexing.');
            end
            s = [s.x(ind.subs{1}),s.y(ind.subs{1})];
         else
            % For any other indexing, call the builtin method
            % (allows accessing public properties and calling
            % methods using the dot notation).
            s = builtin('subsref',s,ind);
         end
      end
      
      % END   Overloaded method, returns index to last element.
      function ii = end(s,k,n)
         if k~=1 || n~=1
            error('Illegal indexing.');
         end
         ii = length(s.x);
      end

      function o = double(s)
      % DOUBLE   Overloaded method, returns coordinate array.
      %   DOUBLE(S) returns an array of size LENGTH(S)X2.
         o = [s.x,s.y];
      end
      
      function o = dip_image(s)
      % DIP_IMAGE   Overloaded method for the DIP_IMAGE constructor.
      %   DIP_IMAGE(S) returns a binary DIP_IMAGE object, foreground
      %   is the convex hull of the snake S.

      % (based on stuff in @dip_image/convhull.m)
         o = newim(s.imsz,'bin');
         strides = [s.imsz(2);1];
         indx = bresenham2([s.x(end),s.y(end)],[s.x(1),s.y(1)])*strides;
         for ii = 2:length(s)
            indx = [indx;bresenham2([s.x(ii-1),s.y(ii-1)],[s.x(ii),s.y(ii)])*strides];
         end
         indx = unique(indx);
         o(indx) = 1;
         o = ~dip_binarypropagation(o&0,~o,1,0,1); % Propagation from border, low connect.
      end

      function s = minimize(s,varargin)
      % MINIMIZE   Move the snake.
      %   S.MINIMIZE(...) or MINIMIZE(S,...) is the same as
      %   SNAKEMINIMIZE(S,...).
      %   See also snakeminimize.
         s = snakeminimize(s,varargin{:});
      end
      
   end
end


%
% computes coordinates of points along line from pt1 to pt2
% (copied from @dip_image/convhull.m)
function line = bresenham2(pt1,pt2)
point = pt2-pt1;
N = max(abs(point)); % the number of pixels needed.
if N==0
   line = pt1;
   return;
end
ii = (0:N)';    % this is better than (0:N)/N, because of round-off errors.
x = ii*(point(1)/N)+pt1(1);
y = ii*(point(2)/N)+pt1(2);
line = round([x,y]);
end
