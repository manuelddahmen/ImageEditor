%SNAKEMINIMIZE   Minimizes the energy function of a snake
%
% SYNOPSIS:
%  snake_out = snakeminimize(snake_in,Fext,alpha,beta,stepsz,kappa,iterations)
%
% PARAMETERS:
%  snake_in :   initial snake, object of type DIP_SNAKE
%  Fext :       external force field, 2D-2D vector image
%               for example: gradient, gvf, vfc
%  alpha :      elasticity parameter (membrane)
%  beta :       rigidity parameter (thin plate)
%  stepsz :     step size
%  kappa :      balloon force (negative for inwards force)
%  iterations : number of iterations performed
%
% DEFAULTS:
%  alpha = 0.2
%  beta = 0.4
%  stepsz = 1
%  kappa = 0
%  iterations = 20
%
% EXAMPLE:
%  a = noise(50+100*gaussf(rr>85,2),'gaussian',20)
%  f = gradient(gradmag(a,5));
%  f = f./max(norm(f));
%  x = 100+30*cos(0:0.1:2*pi); y = 150+40*sin(0:0.1:2*pi);
%  s = dip_snake([x',y'])
%  s = snakeminimize(s,f,0.01,100,3,0.3,20)
%  s = snakeminimize(s,f,0.01,100,3,0.3,20)
%  s = snakeminimize(s,f,0.01,100,3,0.3,20)
%  s = snakeminimize(s,f,0.01,100,3,0.3,20)
%  s = snakeminimize(s,f,0.01,100,3,0.3,20)
%
% NOTE:
%  This function requires at least MATLAB version 7.6!
%
% LITERATURE:
%  M. Kass, A. Witkin, D. Terzopoulos, "Snakes: Active Contour Models",
%     Int. J. of Computer Vision 4(1):321-331 (1988)
%  L.D. Cohen, I. Cohen, "Finite-element methods for active contour models and
%     balloons for 2-D and 3-D images", IEEE TPAMI 15(11):1131-1147 (1993)
%
% SEE ALSO: dip_snake

% (C) Copyright 2009-2010, All rights reserved.
% Cris Luengo, Uppsala, 18 September 2009.
%
% 4 March 2010: Edited for compatability with DIPimage releases after 2.1.

function s = snakeminimize(varargin)

if ( exist('matlabver_ge')==2 && ~matlabver_ge([7,6]) ) || ( exist('matlabversion')==2 && matlabversion<7.6 )
   error('The dip_snake object is not supported on your version of MATLAB.')
end

d = struct('menu','Segmentation',...
           'display','Minimize snake energy',...
           'inparams',struct('name',       {'snake_in',    'Fext',                'alpha',     'beta',    'stepsz',    'kappa',       'iterations'},...
                             'description',{'Input snake', 'External force',      'Elasticity','Rigidity','Step size','Balloon force','Iterations'},...
                             'type',       {'snake',       'image',               'array',     'array',   'array',    'array',        'array'},...
                             'dim_check',  {0,             2,                     0,           0,         0,          0,              0},...
                             'range_check',{[],            {'real','tensor'},     'R+',        'R+',      'R+',       'R',            'N'},...
                             'required',   {1,             1,                     0,           0,         0,          0,              0},...
                             'default',    {'dip_snake(x)','gradient(gradmag(a))',0.2,         0.4,       1,          1,              20}...
                            ),...
           'outparams',struct('name',{'snake_out'},...
                              'description',{'Output snake'},...
                              'type',{'snake'}...
                              )...
          );
if nargin == 1
   s = varargin{1};
   if ischar(s) & strcmp(s,'DIP_GetParamList')
      s = d;
      return
   end
end
try
   [s,f,alpha,beta,stepsz,kappa,iterations] = getparams(d,varargin{:});
catch
   if ~isempty(paramerror)
      error(paramerror)
   else
      error(firsterr)
   end
end

if prod(imarsize(f))~=2
   error('The external force image must have 2D vectors ');
end
[maxx,maxy] = imsize(f);
if any(s.imsz > [maxx,maxy])
   error('The snake is defined on an image larger than the given external force image.');
end
s.imsz = [maxx,maxy];
maxx = maxx-1;
maxy = maxy-1;

% Do the snake!
md = 1; % The average distance we want to keep between points.
s = resample(s,md);
%h = disp(s);
P = compute_matrix(length(s),alpha,beta,stepsz);
for ii = 1:iterations
   %disp(['iteration ii=',num2str(ii),', snake length = ',num2str(length(s))])

   % Do we need to resample the snake?
   d = sqrt( diff(s.x).^2 + diff(s.y).^2 );
   if any(d<md/3) || any(d>md*3)
      %disp('resampling snake')
      s = resample(s,md);
      P = compute_matrix(length(s),alpha,beta,stepsz);
   end
   
   % Calculate external force
   coords = [s.x,s.y];
   fex = get_subpixel(f{1},coords,'linear');
   fey = get_subpixel(f{2},coords,'linear');

   % Calculate balloon force
   if kappa~=0
      b = [coords(2:end,:);coords(1,:)] - [coords(end,:);coords(1:end-1,:)];
      m = sqrt(sum(b.^2,2));
      bx =  b(:,2)./m;
      by = -b(:,1)./m;
      % Add balloon force to external force
      fex = fex+kappa*bx;
      fey = fey+kappa*by;
   end

   % Move control points
   x = P*(s.x+stepsz*fex);
   y = P*(s.y+stepsz*fey);

   % Constrict points to image
   x(x<0) = 0;
   x(x>maxx) = maxx;
   y(y<0) = 0;
   y(y>maxy) = maxy;
   
   % Update snake
   s.x = x;
   s.y = y;   

   %if mod(ii,10)
   %   disp(s,h);
   %   drawnow;
   %   pause(0.5);
   %end

end


% The matrix P = (tau*A+I)^-1
function P = compute_matrix(N,alpha,beta,stepsz)

a = stepsz*(2*alpha+6*beta)+1;
b = stepsz*(-alpha-4*beta);
c = stepsz*beta;
P = diag(repmat(a,1,N));
P = P + diag(repmat(b,1,N-1), 1) + diag(   b, -N+1);
P = P + diag(repmat(b,1,N-1),-1) + diag(   b,  N-1);
P = P + diag(repmat(c,1,N-2), 2) + diag([c,c],-N+2);
P = P + diag(repmat(c,1,N-2),-2) + diag([c,c], N-2);
P = inv(P);
