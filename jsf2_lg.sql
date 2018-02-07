-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Czas generowania: 29 Maj 2016, 17:31
-- Wersja serwera: 10.1.13-MariaDB
-- Wersja PHP: 7.0.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `jsf2_lg`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `ksiazka`
--

CREATE TABLE `ksiazka` (
  `isbn` int(14) NOT NULL,
  `tytul` varchar(16) COLLATE utf8_polish_ci NOT NULL,
  `opis` varchar(48) COLLATE utf8_polish_ci NOT NULL,
  `id_wydawnictwa` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `ksiazka`
--

INSERT INTO `ksiazka` (`isbn`, `tytul`, `opis`, `id_wydawnictwa`) VALUES
(1, 'dsadada', 'abcdsasdasda', 4),
(3, 'abc', 'bac', 3),
(5, 'abc', 'dfsdfs', 5),
(7, 'test', 'nie', 1),
(8, 'rat', 'tarara', 4),
(9, 'rity', 'tity', 3),
(10, 'jam', 'ci jest', 4);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `wydawnictwo`
--

CREATE TABLE `wydawnictwo` (
  `id_wydawnictwa` int(3) NOT NULL,
  `nazwa` varchar(16) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `wydawnictwo`
--

INSERT INTO `wydawnictwo` (`id_wydawnictwa`, `nazwa`) VALUES
(1, 'dobre'),
(2, 'najlepsze'),
(3, 'jeszcze lepsze'),
(4, 'lolowate'),
(5, 'najlepsiejsze'),
(6, 'jaj');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indexes for table `ksiazka`
--
ALTER TABLE `ksiazka`
  ADD PRIMARY KEY (`isbn`);

--
-- Indexes for table `wydawnictwo`
--
ALTER TABLE `wydawnictwo`
  ADD PRIMARY KEY (`id_wydawnictwa`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `wydawnictwo`
--
ALTER TABLE `wydawnictwo`
  MODIFY `id_wydawnictwa` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
