--classrooms
INSERT INTO classrooms (id, name)
VALUES ('7e41c62b-222e-4a92-a3c1-f1b6b634b32d', '101'),
       (uuid_generate_v4(), '103'),
       (uuid_generate_v4(), '207'),
       (uuid_generate_v4(), '210'),
       (uuid_generate_v4(), '305');

--groups
INSERT INTO groups (id, name)
VALUES ('987fbc97-4bed-5078-9f07-9141ba07c9f3', 'FLA-101'),
       (uuid_generate_v4(), 'FLA-203'),
       (uuid_generate_v4(), 'FLA-306'),
       (uuid_generate_v4(), 'FLA-402'),
       (uuid_generate_v4(), 'FLA-507');

--courses
INSERT INTO courses (id, name, description)
VALUES ('4f5a5568-8f09-47c7-9f6b-5fb4f6a90d47', 'Aviation Fundamentals',
        'Introduction to aviation engineering and aerodynamics'),
       (uuid_generate_v4(), 'Material Science',
        'Study of materials used in aviation manufacturing'),
       (uuid_generate_v4(), 'Aerodynamics', 'Principles of aerodynamics and fluid dynamics'),
       (uuid_generate_v4(), 'Descriptive Geometry',
        'Fundamentals of descriptive geometry and technical drawing'),
       (uuid_generate_v4(), 'Avionics Systems',
        'Overview of electronic and avionic systems in aircraft'),
       (uuid_generate_v4(), 'Engine Principles',
        'Study of aviation engine types and their operation principles'),
       (uuid_generate_v4(), 'Aircraft Maintenance',
        'Basics of aircraft maintenance and repair processes'),
       (uuid_generate_v4(), 'Flight Safety', 'Principles of flight safety and accident prevention'),
       (uuid_generate_v4(), 'Mathematics', 'Mathematical foundations for aviation specialists'),
       (uuid_generate_v4(), 'Physics',
        'Physics principles applicable in aviation and aerospace engineering');

--users (admins)
insert into users (id, email, password, first_name, last_name, gender)
values ('251ce75d-3a2e-4384-861a-2bd10569e28e', 'odaverin0@joomla.org', 'wC3)voToIslVs}.4', 'Olympie', 'Daverin',
        'Female');

--users (teachers)
insert into users (id, email, password, first_name, last_name, gender)
values ('a7b4c6d8-e2f3-471b-8f62-3e2a7d9c3f5e', 'fdalgarnocht1@illinois.edu', 'aF2"f{rx~cs\y(ee', 'Fidelio',
        'Dalgarnocht', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'kbundy2@epa.gov', 'rQ9/P}F\f//', 'Katinka', 'Bundy', 'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'koger3@sun.com', 'mP8%_g\||.+''N', 'Kelsey', 'Oger', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'ssherwood4@omniture.com', 'xU3%\eHdd', 'Sebastiano', 'Sherwood',
        'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'stitmus5@nsw.gov.au', 'lQ0\dO2gMJ70ur\l', 'Sabine', 'Titmus',
        'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'bsizzey6@wikimedia.org', 'dC9.G@v1', 'Bil', 'Sizzey', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'rdowtry7@merriam-webster.com', 'tB9@@hzc<!{X43', 'Raynor', 'Dowtry',
        'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'ronoland8@admin.ch', 'oP5+Dg+/$', 'Robinetta', 'O''Noland', 'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'mklaffs9@exblog.jp', 'yO1"|rB~\', 'Munroe', 'Klaffs', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'csherra@jigsy.com', 'pU6|8&d5q2\)', 'Carmine', 'Sherr', 'Male');

--users (students)
insert into users (id, email, password, first_name, last_name, gender)
values ('8d849109-9ba5-46a5-8807-ba093306a7b8', 'vmicallef0@ow.ly', 'qC2~ie3&', 'Vannie', 'Micallef', 'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'tsalmon1@mlb.com', 'nE2+y8ot3', 'Terrance', 'Salmon', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'abrabon2@icq.com', 'jG7>7#)/39VQ', 'Amalie', 'Brabon', 'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'blarder3@homestead.com', 'oQ7''@B@$`n\uk2G2', 'Bertie', 'Larder',
        'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'jgrieveson4@diigo.com', 'qD4!sP=++KqkOf', 'Jewel', 'Grieveson',
        'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'cdudden5@vk.com', 'dD3.JJAs,R>s', 'Chelsey', 'Dudden', 'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'tmackenny6@wordpress.com', 'bT1\z3)1', 'Turner', 'MacKenny', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'dhateley7@redcross.org', 'uF5.R!HFv,', 'Druci', 'Hateley', 'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'emclardie8@istockphoto.com', 'zU6)3(FH<', 'Eva', 'McLardie', 'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'aburds9@ovh.net', 'uI2{A1,*WY''T+1S', 'Adelbert', 'Burds', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'ncroixa@1688.com', 'yT7_c`ci!>', 'Neddie', 'Croix', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'dmccarrickb@phoca.cz', 'vA5"r1dA7s8T>', 'Davie', 'McCarrick', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'adessentc@vkontakte.ru', 'cW4''h\CtGY', 'Ara', 'Dessent', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'gabramowskyd@ox.ac.uk', 'eU3@uqct', 'Gerty', 'Abramowsky', 'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'kdallmane@yahoo.co.jp', 'kU4_HjMTB!|mXEqI', 'Kittie', 'Dallman',
        'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'kjordisonf@webmd.com', 'mN1#sS%!@PvL`', 'Kylie', 'Jordison', 'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'pheselwoodg@discuz.net', 'xP4_Ou&r{Z', 'Patricio', 'Heselwood',
        'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'ushelsherh@parallels.com', 'lL9>I>2o<V''$Q', 'Ulrich', 'Shelsher',
        'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'dyeolandi@tmall.com', 'uO0%NQZ8', 'Dean', 'Yeoland', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'ocheversj@altervista.org', 'oT0|PKz42Q4?i?uX', 'Obidiah', 'Chevers',
        'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'yfurzeyk@networkadvertising.org', 'oR7%3GT9(IZ"`"w', 'Yvon', 'Furzey',
        'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'jondrasekl@unesco.org', 'jF3)rSlF31TfCt', 'Jocelin', 'Ondrasek',
        'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'nmclauchlinm@boston.com', 'xL5|7o,h', 'Nadia', 'McLauchlin', 'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'oarelesn@foxnews.com', 'kO7(cx''?}a#', 'Osbert', 'Areles', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'succelio@myspace.com', 'gJ4<DLGd="<)', 'Sherri', 'Ucceli', 'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'nbernip@businessweek.com', 'kX8"0|LWhp0_', 'Nonah', 'Berni', 'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'tmcnirlanq@webmd.com', 'nA1{DbhLLZ6', 'Thom', 'McNirlan', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'lgeevesr@about.me', 'rH8*1BAb', 'Laurette', 'Geeves', 'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'gnormans@tinyurl.com', 'jB1.S5t.X7lKE', 'Gus', 'Norman', 'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'tbyert@studiopress.com', 'mF9\S|!F7p8Z', 'Thomasina', 'Byer',
        'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'mlewsleyu@independent.co.uk', 'kR2!dM(H%*%', 'Madelin', 'Lewsley',
        'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'smyriev@opensource.org', 'dD9?_1g,XWL,', 'Selena', 'Myrie', 'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'zbernardesw@upenn.edu', 'mV1|N+oagCW7L', 'Zedekiah', 'Bernardes',
        'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'dkinnarx@wp.com', 'eE2?l''`R', 'Dode', 'Kinnar', 'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'sjedrzejewskiy@marriott.com', 'bE0`?3Zzx', 'Sue', 'Jedrzejewski',
        'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'mquilliamz@g.co', 'lA5~whFjM', 'Maggy', 'Quilliam', 'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'blane10@fc2.com', 'mU7**sV9~4WS>UP', 'Bernarr', 'Lane', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'spetrashkov11@booking.com', 'yN9#Fnm9lH4B', 'Simmonds', 'Petrashkov',
        'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'phay12@myspace.com', 'aH7*nzth2s/8dE', 'Pansy', 'Hay', 'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'lwogdon13@fc2.com', 'eS3!`0+AT|`', 'Lenna', 'Wogdon', 'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'jmollnar14@creativecommons.org', 'gA2>r.iAyX%Y23W', 'Joseito',
        'Mollnar', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'gbarthelme15@europa.eu', 'dY0~a*=elx+VNUb', 'Giulio', 'Barthelme',
        'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'rhamer16@slideshare.net', 'wT8}/@HbW10,z', 'Rice', 'Hamer', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'bparley17@alibaba.com', 'iC2,=aZi%#', 'Betsy', 'Parley', 'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'fcovill18@toplist.cz', 'eD8"&C$+~+~', 'Fenelia', 'Covill', 'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'nfilippazzo19@flickr.com', 'yU2=e"XhN`Yp6P', 'Nedi', 'Filippazzo',
        'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'galbro1a@facebook.com', 'sA0''%P5_&', 'Gertruda', 'Albro', 'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'plangstaff1b@angelfire.com', 'pI6~Thi@@#`o', 'Phelia', 'Langstaff',
        'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'erozea1c@nydailynews.com', 'rU4''I)1%ln', 'Eddy', 'Rozea', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'fblowick1d@earthlink.net', 'kQ4?lKe#V)$B''g+', 'Farlee', 'Blowick',
        'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'eblench1e@zimbio.com', 'xL4?*TL%i', 'Emalia', 'Blench', 'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'bdiack1f@state.tx.us', 'nN2`c/1(', 'Bernard', 'Diack', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'cminster1g@google.nl', 'dI1*Hw}N2FZ?8', 'Cornall', 'Minster', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'shantusch1h@prweb.com', 'sX1)H$n.AI80GFN', 'Sibyl', 'Hantusch',
        'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'dharcombe1i@jigsy.com', 'rG0)P)ng0HZDP,%', 'Denyse', 'Harcombe',
        'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'ydoe1j@census.gov', 'kZ1<pV!{G(%.,@_', 'Yurik', 'Doe', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'wharcus1k@google.fr', 'gP7=ZV&mI)DBX!p', 'Web', 'Harcus', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'rwarboys1l@google.cn', 'xB4*}Hs%Ec4', 'Roz', 'Warboys', 'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'lbazelle1m@addthis.com', 'mV4+p#t4cB)8/B2#', 'Lorin', 'Bazelle',
        'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'kheard1n@hugedomains.com', 'jR3\w{#iM{c', 'Kalila', 'Heard', 'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'cdaulby1o@census.gov', 'iF2@0#Wl&KAKEJf', 'Constancia', 'Daulby',
        'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'ahinkes1p@themeforest.net', 'jP6&v#8b', 'Ann-marie', 'Hinkes',
        'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'zdunthorn1q@ehow.com', 'rX7,\ml\e{iU`I', 'Zak', 'Dunthorn', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'mattfield1r@si.edu', 'qD2+~qg"', 'Marcella', 'Attfield', 'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'hbatte1s@google.com.hk', 'oE2`|=N@"gif?"X1', 'Hillary', 'Batte',
        'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'efrondt1t@chron.com', 'vL6.o?R\', 'Evelyn', 'Frondt', 'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'achildrens1u@wikimedia.org', 'hN2}D{=GXWZwkd', 'Almeda', 'Childrens',
        'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'tkestin1v@angelfire.com', 'iX5|WS@,s$7_=mrl', 'Torin', 'Kestin',
        'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'dnairns1w@noaa.gov', 'gG0*(DfAA21j(k<r', 'Darn', 'Nairns', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'earnaud1x@facebook.com', 'jT5/rS4.=xzfgQ', 'Esdras', 'Arnaud', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'emorville1y@examiner.com', 'iU5&sd(Vj`A|K', 'Elysha', 'Morville',
        'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'wcadell1z@spiegel.de', 'sY2_pQb$A2h', 'Wade', 'Cadell', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'agonthard20@mashable.com', 'fZ0,G@''#', 'Alaster', 'Gonthard', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'ccochet21@mapy.cz', 'jV3{|hGN~34', 'Christie', 'Cochet', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'wpretsel22@t.co', 'lJ3/h92v7txyRr', 'Waldo', 'Pretsel', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'mfarney23@newsvine.com', 'nU6>_Mlp0sS', 'Marti', 'Farney', 'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'tpaulack24@intel.com', 'lJ0|#GKMds', 'Tibold', 'Paulack', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'leastridge25@yellowbook.com', 'vQ7$Y.m.u($NOgK2', 'Leann', 'Eastridge',
        'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'jgosart26@themeforest.net', 'rW7`C>"jC@', 'Jamima', 'Gosart',
        'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'gcanelas27@yandex.ru', 'hV2&g*sWDMGf', 'Grady', 'Canelas', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'emcanellye28@samsung.com', 'qS6#|87rFL', 'Efrem', 'McAnellye', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'sirlam29@github.com', 'nT3%#Ev=jx''', 'Sonnie', 'Irlam', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'jjanaszkiewicz2a@gov.uk', 'mJ9&C~rLP~{Bn&', 'Johanna', 'Janaszkiewicz',
        'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'rofinan2b@amazon.co.uk', 'hB8+LLw+Gy', 'Rene', 'O''Finan', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'apinckstone2c@wufoo.com', 'xK0_/~v442HJ', 'Angele', 'Pinckstone',
        'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'npozzo2d@msn.com', 'uV5\#\7q#<c+N&&''', 'Nealson', 'Pozzo', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'gslowley2e@constantcontact.com', 'eZ0\$=!{', 'Georgy', 'Slowley',
        'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'cflintoffe2f@acquirethisname.com', 'oQ8__>b%!s', 'Carlo', 'Flintoffe',
        'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'kcargill2g@timesonline.co.uk', 'hO2,/.\P', 'Katine', 'Cargill',
        'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'bbannon2h@slideshare.net', 'zJ0_z)V6LqX?', 'Bary', 'Bannon', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'jbowld2i@businessinsider.com', 'jX9&|(IJ6fm6?Tw', 'Jerrie', 'Bowld',
        'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'wbuzine2j@1und1.de', 'zJ6?@oD1P', 'Webster', 'Buzine', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'tleggon2k@rediff.com', 'jI6_ufIvB''*', 'Tudor', 'Leggon', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'adenisovo2l@freewebs.com', 'rW5+jA<Ns<!B=u', 'Aleksandr', 'Denisovo',
        'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'jcattach2m@phoca.cz', 'tI0}TfsHYZ', 'Jelene', 'Cattach', 'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'sboon2n@wp.com', 'gJ5''zP|Za)QF', 'Skippie', 'Boon', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'tpietrasik2o@engadget.com', 'qW9*&#U+Jgxy', 'Thomasin', 'Pietrasik',
        'Female');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'sfreyne2p@google.com', 'sN8)WJS6vOF', 'Somerset', 'Freyne', 'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'fspencers2q@shutterfly.com', 'xY9/2WO?vyWFu', 'Frasco', 'Spencers',
        'Male');
insert into users (id, email, password, first_name, last_name, gender)
values (uuid_generate_v4(), 'lseccombe2r@dion.ne.jp', 'jT3<\&&/Ew$c`,Np', 'Lance', 'Seccombe',
        'Male');

--admins
INSERT INTO admins (user_id)
SELECT id
FROM users
WHERE email = 'odaverin0@joomla.org';

--teachers
INSERT INTO teachers (user_id)
SELECT id
FROM users
WHERE email IN ('fdalgarnocht1@illinois.edu',
                'kbundy2@epa.gov',
                'koger3@sun.com',
                'ssherwood4@omniture.com',
                'stitmus5@nsw.gov.au',
                'bsizzey6@wikimedia.org',
                'rdowtry7@merriam-webster.com',
                'ronoland8@admin.ch',
                'mklaffs9@exblog.jp',
                'csherra@jigsy.com'
  );

--students
DO
$$
  DECLARE
    group_ids     UUID[];
    group_counter INT := 0;
    student_ids   UUID[];
  BEGIN
    SELECT array_agg(id ORDER BY name) INTO group_ids FROM groups;

    SELECT array_agg(id)
    INTO student_ids
    FROM users
    WHERE email IN ('vmicallef0@ow.ly', 'tsalmon1@mlb.com', 'abrabon2@icq.com',
                    'blarder3@homestead.com', 'jgrieveson4@diigo.com', 'cdudden5@vk.com',
                    'tmackenny6@wordpress.com', 'dhateley7@redcross.org',
                    'emclardie8@istockphoto.com', 'aburds9@ovh.net', 'ncroixa@1688.com',
                    'dmccarrickb@phoca.cz', 'adessentc@vkontakte.ru',
                    'gabramowskyd@ox.ac.uk', 'kdallmane@yahoo.co.jp', 'kjordisonf@webmd.com',
                    'pheselwoodg@discuz.net', 'ushelsherh@parallels.com', 'dyeolandi@tmall.com',
                    'ocheversj@altervista.org', 'yfurzeyk@networkadvertising.org',
                    'jondrasekl@unesco.org', 'nmclauchlinm@boston.com', 'oarelesn@foxnews.com',
                    'succelio@myspace.com',
                    'nbernip@businessweek.com', 'tmcnirlanq@webmd.com', 'lgeevesr@about.me',
                    'gnormans@tinyurl.com', 'tbyert@studiopress.com',
                    'mlewsleyu@independent.co.uk',
                    'smyriev@opensource.org', 'zbernardesw@upenn.edu', 'dkinnarx@wp.com',
                    'sjedrzejewskiy@marriott.com', 'mquilliamz@g.co', 'blane10@fc2.com',
                    'spetrashkov11@booking.com',
                    'phay12@myspace.com', 'lwogdon13@fc2.com', 'jmollnar14@creativecommons.org',
                    'gbarthelme15@europa.eu', 'rhamer16@slideshare.net',
                    'bparley17@alibaba.com',
                    'fcovill18@toplist.cz', 'nfilippazzo19@flickr.com', 'galbro1a@facebook.com',
                    'plangstaff1b@angelfire.com', 'erozea1c@nydailynews.com',
                    'fblowick1d@earthlink.net',
                    'eblench1e@zimbio.com', 'bdiack1f@state.tx.us', 'cminster1g@google.nl',
                    'shantusch1h@prweb.com', 'dharcombe1i@jigsy.com', 'ydoe1j@census.gov',
                    'wharcus1k@google.fr', 'rwarboys1l@google.cn', 'lbazelle1m@addthis.com',
                    'kheard1n@hugedomains.com', 'cdaulby1o@census.gov',
                    'ahinkes1p@themeforest.net',
                    'zdunthorn1q@ehow.com',
                    'mattfield1r@si.edu', 'hbatte1s@google.com.hk', 'efrondt1t@chron.com',
                    'achildrens1u@wikimedia.org', 'tkestin1v@angelfire.com',
                    'dnairns1w@noaa.gov',
                    'earnaud1x@facebook.com', 'emorville1y@examiner.com',
                    'wcadell1z@spiegel.de',
                    'agonthard20@mashable.com', 'ccochet21@mapy.cz', 'wpretsel22@t.co',
                    'mfarney23@newsvine.com',
                    'tpaulack24@intel.com', 'leastridge25@yellowbook.com',
                    'jgosart26@themeforest.net', 'gcanelas27@yandex.ru',
                    'emcanellye28@samsung.com',
                    'sirlam29@github.com', 'jjanaszkiewicz2a@gov.uk', 'rofinan2b@amazon.co.uk',
                    'apinckstone2c@wufoo.com', 'npozzo2d@msn.com',
                    'gslowley2e@constantcontact.com',
                    'cflintoffe2f@acquirethisname.com',
                    'kcargill2g@timesonline.co.uk', 'bbannon2h@slideshare.net',
                    'jbowld2i@businessinsider.com', 'wbuzine2j@1und1.de',
                    'tleggon2k@rediff.com',
                    'adenisovo2l@freewebs.com', 'jcattach2m@phoca.cz', 'sboon2n@wp.com',
                    'tpietrasik2o@engadget.com', 'sfreyne2p@google.com',
                    'fspencers2q@shutterfly.com', 'lseccombe2r@dion.ne.jp'
      );

    FOR i IN 1..array_length(student_ids, 1)
      LOOP
        INSERT INTO students (user_id, group_id)
        VALUES (student_ids[i],
                group_ids[(group_counter % array_length(group_ids, 1)) + 1]);

        group_counter := group_counter + 1;
      END LOOP;
  END
$$;


--teachers_courses
INSERT INTO teachers_courses (teacher_id, course_id)
SELECT (SELECT id FROM users WHERE email = 'fdalgarnocht1@illinois.edu'),
       (SELECT id FROM courses WHERE name = 'Mathematics')
UNION ALL
SELECT (SELECT id FROM users WHERE email = 'fdalgarnocht1@illinois.edu'),
       (SELECT id FROM courses WHERE name = 'Physics')
UNION ALL
SELECT (SELECT id FROM users WHERE email = 'kbundy2@epa.gov'),
       (SELECT id FROM courses WHERE name = 'Mathematics')
UNION ALL
SELECT (SELECT id FROM users WHERE email = 'kbundy2@epa.gov'),
       (SELECT id FROM courses WHERE name = 'Aviation Fundamentals')
UNION ALL
SELECT (SELECT id FROM users WHERE email = 'koger3@sun.com'),
       (SELECT id FROM courses WHERE name = 'Material Science')
UNION ALL
SELECT (SELECT id FROM users WHERE email = 'ssherwood4@omniture.com'),
       (SELECT id FROM courses WHERE name = 'Aerodynamics')
UNION ALL
SELECT (SELECT id FROM users WHERE email = 'stitmus5@nsw.gov.au'),
       (SELECT id FROM courses WHERE name = 'Descriptive Geometry')
UNION ALL
SELECT (SELECT id FROM users WHERE email = 'stitmus5@nsw.gov.au'),
       (SELECT id FROM courses WHERE name = 'Avionics Systems')
UNION ALL
SELECT (SELECT id FROM users WHERE email = 'bsizzey6@wikimedia.org'),
       (SELECT id FROM courses WHERE name = 'Engine Principles')
UNION ALL
SELECT (SELECT id FROM users WHERE email = 'bsizzey6@wikimedia.org'),
       (SELECT id FROM courses WHERE name = 'Physics')
UNION ALL
SELECT (SELECT id FROM users WHERE email = 'rdowtry7@merriam-webster.com'),
       (SELECT id FROM courses WHERE name = 'Aircraft Maintenance')
UNION ALL
SELECT (SELECT id FROM users WHERE email = 'rdowtry7@merriam-webster.com'),
       (SELECT id FROM courses WHERE name = 'Flight Safety')
UNION ALL
SELECT (SELECT id FROM users WHERE email = 'ronoland8@admin.ch'),
       (SELECT id FROM courses WHERE name = 'Flight Safety')
UNION ALL
SELECT (SELECT id FROM users WHERE email = 'mklaffs9@exblog.jp'),
       (SELECT id FROM courses WHERE name = 'Material Science')
UNION ALL
SELECT (SELECT id FROM users WHERE email = 'mklaffs9@exblog.jp'),
       (SELECT id FROM courses WHERE name = 'Aerodynamics')
UNION ALL
SELECT (SELECT id FROM users WHERE email = 'csherra@jigsy.com'),
       (SELECT id FROM courses WHERE name = 'Avionics Systems')
UNION ALL
SELECT (SELECT id FROM users WHERE email = 'csherra@jigsy.com'),
       (SELECT id FROM courses WHERE name = 'Aircraft Maintenance');

--course_assignments
INSERT INTO course_assignments (id, group_id, course_id, teacher_id)
VALUES ('123e4567-e89b-12d3-a456-426614174000', '987fbc97-4bed-5078-9f07-9141ba07c9f3',
        '4f5a5568-8f09-47c7-9f6b-5fb4f6a90d47', 'a7b4c6d8-e2f3-471b-8f62-3e2a7d9c3f5e'),
       (uuid_generate_v4(), (SELECT id FROM groups WHERE name = 'FLA-101'),
        (SELECT id FROM courses WHERE name = 'Mathematics'),
        (SELECT id FROM users WHERE email = 'kbundy2@epa.gov')),
       (uuid_generate_v4(), (SELECT id FROM groups WHERE name = 'FLA-101'),
        (SELECT id FROM courses WHERE name = 'Physics'),
        (SELECT id FROM users WHERE email = 'bsizzey6@wikimedia.org')),
       (uuid_generate_v4(), (SELECT id FROM groups WHERE name = 'FLA-203'),
        (SELECT id FROM courses WHERE name = 'Material Science'),
        (SELECT id FROM users WHERE email = 'mklaffs9@exblog.jp')),
       (uuid_generate_v4(), (SELECT id FROM groups WHERE name = 'FLA-203'),
        (SELECT id FROM courses WHERE name = 'Mathematics'),
        (SELECT id FROM users WHERE email = 'fdalgarnocht1@illinois.edu')),
       (uuid_generate_v4(), (SELECT id FROM groups WHERE name = 'FLA-203'),
        (SELECT id FROM courses WHERE name = 'Physics'),
        (SELECT id FROM users WHERE email = 'fdalgarnocht1@illinois.edu')),
       (uuid_generate_v4(), (SELECT id FROM groups WHERE name = 'FLA-203'),
        (SELECT id FROM courses WHERE name = 'Descriptive Geometry'),
        (SELECT id FROM users WHERE email = 'stitmus5@nsw.gov.au')),
       (uuid_generate_v4(), (SELECT id FROM groups WHERE name = 'FLA-306'),
        (SELECT id FROM courses WHERE name = 'Material Science'),
        (SELECT id FROM users WHERE email = 'koger3@sun.com')),
       (uuid_generate_v4(), (SELECT id FROM groups WHERE name = 'FLA-306'),
        (SELECT id FROM courses WHERE name = 'Aerodynamics'),
        (SELECT id FROM users WHERE email = 'ssherwood4@omniture.com')),
       (uuid_generate_v4(), (SELECT id FROM groups WHERE name = 'FLA-306'),
        (SELECT id FROM courses WHERE name = 'Flight Safety'),
        (SELECT id FROM users WHERE email = 'rdowtry7@merriam-webster.com')),
       (uuid_generate_v4(), (SELECT id FROM groups WHERE name = 'FLA-402'),
        (SELECT id FROM courses WHERE name = 'Aerodynamics'),
        (SELECT id FROM users WHERE email = 'mklaffs9@exblog.jp')),
       (uuid_generate_v4(), (SELECT id FROM groups WHERE name = 'FLA-402'),
        (SELECT id FROM courses WHERE name = 'Avionics Systems'),
        (SELECT id FROM users WHERE email = 'csherra@jigsy.com')),
       (uuid_generate_v4(), (SELECT id FROM groups WHERE name = 'FLA-402'),
        (SELECT id FROM courses WHERE name = 'Aircraft Maintenance'),
        (SELECT id FROM users WHERE email = 'csherra@jigsy.com')),
       (uuid_generate_v4(), (SELECT id FROM groups WHERE name = 'FLA-402'),
        (SELECT id FROM courses WHERE name = 'Flight Safety'),
        (SELECT id FROM users WHERE email = 'ronoland8@admin.ch')),
       (uuid_generate_v4(), (SELECT id FROM groups WHERE name = 'FLA-507'),
        (SELECT id FROM courses WHERE name = 'Avionics Systems'),
        (SELECT id FROM users WHERE email = 'stitmus5@nsw.gov.au')),
       (uuid_generate_v4(), (SELECT id FROM groups WHERE name = 'FLA-507'),
        (SELECT id FROM courses WHERE name = 'Engine Principles'),
        (SELECT id FROM users WHERE email = 'bsizzey6@wikimedia.org')),
       (uuid_generate_v4(), (SELECT id FROM groups WHERE name = 'FLA-507'),
        (SELECT id FROM courses WHERE name = 'Aircraft Maintenance'),
        (SELECT id FROM users WHERE email = 'rdowtry7@merriam-webster.com'));

--lessons
INSERT INTO lessons (id, date, start_time, end_time, classroom_id, course_assignment_id)
VALUES ('5e12f539-d1b3-46a7-be8d-c21a9edfac11', '2024-03-30', '08:00:00', '09:30:00', '7e41c62b-222e-4a92-a3c1-f1b6b634b32d', '123e4567-e89b-12d3-a456-426614174000');

