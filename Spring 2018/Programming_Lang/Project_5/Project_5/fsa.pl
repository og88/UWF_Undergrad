%%******************************************%%
%% This file holds the rules and funtions   %%
%% for an fsa in prolog                     %%
%%******************************************%%

%%******************************************%%
%% FACTS                                    %%
%%******************************************%%                        
accept(1).
accept(3).

input(0,x,x).
input(0,y,y).
input(1,x,x).
input(2,x,x).
input(2,y,y).
input(3,x,x).
input(3,z,z).
input(4,x,x).
input(4,a,a).

%%****************************************%%
%% RULES                                  %%
%%****************************************%% 


good([H|T]) :- state0([H|T],H,T).

state0([H|T],H,T) :- input(0,H,x), state0(T,_,_).
state0([H|T],H,T) :- input(0,H,y), state1(T,_,_).
state0([],_,_) :- accept(0).

state1([H|T],H,T) :- input(1,H,x), state2(T,_,_).
state1([],_,_) :- accept(1).

state2([H|T],H,T) :- input(2,H,x), state2(T,_,_).
state2([H|T],H,T) :- input(2,H,y), state3(T,_,_).
state2([],_,_) :- accept(2).

state3([H|T],H,T) :- input(3,H,x), state3(T,_,_).
state3([H|T],H,T) :- input(3,H,z), state4(T,_,_).
state3([],_,_) :- accept(3).

state4([H|T],H,T) :- input(4,H,x), state4(T,_,_).
state4([H|T],H,T) :- input(4,H,a), state1(T,_,_).
state4([],_,_) :- accept(4).

