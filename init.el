;; init.el --- Emacs configuration
;; https://realpython.com/blog/python/emacs-the-best-python-editor/
;; INSTALL PACKAGES
					;http://ergoemacs.org/emacs/emacs_package_system.html
					;http://ergoemacs.org/emacs/emacs_installing_packages.html

;;'(better-defaults  dirtree  #tramp  expand-region  key-chord  ein  elpy  flycheck  material-theme  py-autopep8))
(server-start)
(package-initialize t)
;(setq use-package-always-ensure)
;(package-initialize)
;(when (not package-archive-contents)
;  (package-refresh-contents))

(add-to-list 'load-path "~/.emacs.d/m_lisp")
(add-to-list 'load-path "~/.emacs.d/m_lisp/ein")
(add-to-list 'load-path "~/.emacs.d/m_lisp/elpy")
(add-to-list 'load-path "~/.emacs.d/m_lisp/expand-region")
(add-to-list 'load-path "~/.emacs.d/m_lisp/flycheck")
(add-to-list 'load-path "~/.emacs.d/m_lisp/material-theme")

;(load "go-autocomplete")
(load "better-defaults")
(load "key-chord")
(load "py-autopep8")
(load "ein")
(load "elpy")
(load "expand-region")
(load "flycheck")
(load "material-theme")

;(load "dirtree"), (load "tramp")

;; BASIC CUSTOMIZATION
;; --------------------------------------
(setq inhibit-startup-message t) ;; hide the startup message
(load-theme 'material t) ;; load material theme
;;(load-theme 'color-theme-desert t)
(global-linum-mode t) ;; enable line numbers globally
(tool-bar-mode 0) 
(menu-bar-mode 1)
(scroll-bar-mode 1)
;;(fset `yes-or-no-p `y-or-n-p)

;; PYTHON CONFIGURATION
;; --------------------------------------
;(setenv "IPY_TEST_SIMPLE_PROMPT" "1")
;(elpy-enable)
;;(elpy-use-ipython)
;;(setq python-shell-interpreter "ipython"
;;  python-shell-interpreter-args "--simple-prompt")

;; use flycheck not flymake with elpy ; below sing ; was taken out due to strange error msgs ###
;(when (require 'flycheck nil t)
;  (setq elpy-modules (delq 'elpy-module-flymake elpy-modules))
;  (add-hook 'elpy-mode-hook 'flycheck-mode))

;; enable autopep8 formatting on save
(require 'py-autopep8)
;;(add-hook 'elpy-mode-hook 'py-autopep8-enable-on-save)

;; Global variables
;; http://ensime.github.io/editors/emacs/learning/
;; --------------------------------------
(setq
 inhibit-startup-screen t
 create-lockfiles nil
 make-backup-files nil
 column-number-mode t
 scroll-error-top-bottom t
 show-paren-delay 0.5
 use-package-always-ensure t
 sentence-end-double-space nil)

;; buffer local variables
(setq-default
 indent-tabs-mode nil
 tab-width 4
 c-basic-offset 4)

;;(set-face-attribute 'default nil :height 105) ;; Font size
(set-face-attribute 'default nil
                    :family "Consolas" :height 100)
;;(set-language-environment "Korean")
;;(set-fontset-font t 'hangul (font-spec :name "NanumGothicCoding"))

(electric-indent-mode 0) ;; modes
(global-unset-key (kbd "C-z")) ;; global keybindings

;; Global variables
;;### From http://www.47deg.com/blog/scala-development-with-emacs
;; --------------------------------------
;;We have "sbt" and "scala" in /usr/local/bin so add this path to the PATH
;;(setq exec-path (append exec-path '("/usr/local/bin")))
;;(setq exec-path (append exec-path '("/usr/local/sbin")))
;;(setenv "PATH" (shell-command-to-string "/bin/bash -c 'echo -n $PATH'"))


(require 'expand-region)
(global-set-key (kbd "C-=") 'er/expand-region)

;(require 'tramp) ; Adds Remote file edit
;(setq tramp-default-method "scp")

;;(require 'hl-line+) ; Load this file (it will load `hl-line.el')
;; mark-thing (line?))) // thing-cmds.el

;; key chords
(require 'key-chord)
(setq key-chord-one-key-delay 0.2) ; default 0.2
(key-chord-define-global ".." 'er/expand-region)
;;(key-chord-define-global ",," 'mark-lines-current-line
(key-chord-mode +1)

;; init.el ends here
;;`C-h k is describe Emacs keybiding 
;;`I wish:  C-return work in scala as

