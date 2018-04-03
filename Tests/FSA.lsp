(defun state4(L)
(if (NULL L) (princ "Non-accepting State")) 
(setq x (CAR L))
(if (EQUAL 'x X) (state4 (CDR L)))
(if (EQUAL 'a X) (state1 (CDR L)))
)

