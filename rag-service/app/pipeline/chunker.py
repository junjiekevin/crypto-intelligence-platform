from langchain_text_splitters import RecursiveCharacterTextSplitter
from typing import List

class DocumentChunker:
    def __init__(self, chunk_size: int = 1000, chunk_overlap: int = 200):
        self.text_splitter = RecursiveCharacterTextSplitter(
            chunk_size=chunk_size,
            chunk_overlap=chunk_overlap,
            length_function=len,
            is_separator_regex=False,
        )

    def chunk_text(self, text: str) -> List[str]:
        """
        Splits text into chunks.
        """
        if not text:
            return []
        
        # We return the content string of each document object
        docs = self.text_splitter.create_documents([text])
        return [doc.page_content for doc in docs]
